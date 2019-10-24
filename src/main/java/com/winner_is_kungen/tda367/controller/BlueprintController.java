package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Blueprint;
import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.util.EventBusEvent;
import com.winner_is_kungen.tda367.model.util.Tuple;
import com.winner_is_kungen.tda367.view.canvas.InfiniteCanvas;
import com.winner_is_kungen.tda367.controller.ConnectionPointController.ConnectionPointType;

import java.util.ArrayList;
import java.util.HashMap;

import static com.winner_is_kungen.tda367.controller.ConnectionPointController.ConnectionPointEvent.CONNECTION_START_EVENT;

public class BlueprintController extends InfiniteCanvas {
	private Blueprint blueprint;

	private ConnectionPointController connectionStart;
	private boolean connectionInProgress = false;

	private HashMap<String, ComponentController> componentControllers = new HashMap<>();
	private HashMap<String, ConnectionController> connections = new HashMap<>();

	public BlueprintController() {
		this.addEventHandler(CONNECTION_START_EVENT, event -> startConnection(event.getConnectionPoint()));
	}

	/**
	 * Sets which Blueprint this controller should display and interact with.
	 */
	public void setBlueprint(Blueprint blueprint) {
		if (this.blueprint != null) {
			this.blueprint.getEventBus().removeListener(Blueprint.eventComponent, this::onComponentChange);
		}

		this.blueprint = blueprint;


		getChildren().clear();
		componentControllers.clear();
		connections.clear();

		if (this.blueprint != null) {
			for (int i = 0; i < this.blueprint.getSize(); i++) {
				ComponentController cc = ComponentControllerFactory.Create(this.blueprint.getComponent(i));
				componentControllers.put(cc.getID(), cc);
				getChildren().add(cc);
			}

			for(Component c: this.blueprint.getComponentList()){
				for(Tuple<Component,Integer,Integer> connection : this.blueprint.getIncomingConnections(c)){
					this.createConnection(connection.first(),connection.third(),connection.second(),c);
				}

			}

			this.blueprint.getEventBus().addListener(Blueprint.eventComponent, this::onComponentChange);
			this.blueprint.getEventBus().addListener(Blueprint.eventConnection, this::onConnectionChange);
		}
	}

	public Blueprint getCurrentBlueprint() {
		return this.blueprint;
	}

	public void startConnection(ConnectionPointController c1) {
		if (connectionInProgress) {
			completeConnection(c1);
			connectionInProgress = false;
		} else {
			connectionStart = c1;
			connectionInProgress = true;
		}
	}

	private void completeConnection(ConnectionPointController connectionEnd) {
		if (connectionStart == connectionEnd)
			return;                   // Do not create connection between the same point
		if (connectionStart.ioType == connectionEnd.ioType)
			return;   // Do not connect an input to an input and vice versa

		Component c1 = componentControllers.get(connectionStart.getComponentID()).getModel();
		int channel1 = connectionStart.channel;

		Component c2 = componentControllers.get(connectionEnd.getComponentID()).getModel();
		int channel2 = connectionEnd.channel;

		// Allow connections of any order ( input - output | output - input)

		if (connectionStart.ioType == ConnectionPointType.OUTPUT) {
			blueprint.connect(c1, channel1, c2, channel2);
		} else {
			blueprint.connect(c2, channel2, c1, channel1);
		}

		// If the model accepts the connection it will broadcast back with an event to create the connection
	}

	/**
	 * Adds a new component to this controllers Blueprint.
	 *
	 * @param component The component to be added.
	 */

	public void addComponent(Component component) {
		if (blueprint != null) {
			blueprint.addComponent(component);
		} else {
			throw new IllegalStateException("Must set a Blueprint first.");
		}
	}

	/**
	 * Removes a component from this controllers Blueprint.
	 *
	 * @param component The component to be removed.
	 */
	public void removeComponent(Component component) {
		if (blueprint != null) {
			blueprint.removeComponent(component);
		} else {
			throw new IllegalStateException("Must set a Blueprint first.");
		}
	}

	/**
	 * Reacts to Component changes in the Blueprint.
	 * Such as adding or removing Components.
	 *
	 * @param event Event message object.
	 */
	private void onComponentChange(EventBusEvent<Blueprint.ComponentEvent> event) {
		if (event.getMessage().isAdded()) {
			ComponentController newComponent = ComponentControllerFactory.Create(event.getMessage().getAffectedComponent());
			getChildren().add(newComponent);
			componentControllers.put(newComponent.getID(), newComponent);
		} else {
			componentControllers.remove(event.getMessage().getAffectedComponent().getId());
			getChildren().removeIf(
				x -> x instanceof ComponentController && ((ComponentController) x).getModel() == event.getMessage().getAffectedComponent()
			);
		}
	}

	private void onConnectionChange(EventBusEvent<Blueprint.ConnectionEvent> event) {
		Blueprint.ConnectionEvent msg = event.getMessage();
		if (msg.isConnected()) {
			createConnection(msg.getFromComponent(), msg.getOutChannel(), msg.getInChannel(), msg.getToComponent());
		} else {
			removeConnection(msg.getFromComponent(), msg.getOutChannel(), msg.getInChannel(), msg.getToComponent());
		}
	}

	private void createConnection(Component fromComponent, int outChannel, int inChannel, Component toComponent) {
		ComponentController fromCC = componentControllers.get(fromComponent.getId());
		ComponentController toCC = componentControllers.get(toComponent.getId());

		ConnectionPointController fromCP = fromCC.getOutputConnectionPoint(outChannel);
		ConnectionPointController toCP = toCC.getInputConnectionPoint(inChannel);

		String lineID = fromCC.getID() + ":" + outChannel + "->" + inChannel + ":" + toCC.getID();

		ConnectionController connection = new ConnectionController(fromCP, toCP);

		connections.put(lineID, connection);
		this.getChildren().add(connection);

	}

	private void removeConnection(Component fromComponent, int outChannel, int inChannel, Component toComponent) {
		ComponentController fromCC = componentControllers.get(fromComponent.getId());
		ComponentController toCC = componentControllers.get(toComponent.getId());

		String lineID = fromCC.getID() + ":" + outChannel + "->" + inChannel + ":" + toCC.getID();

		ConnectionController toBeRemoved = connections.remove(lineID);
		this.getChildren().remove(toBeRemoved);
	}

	@Override
	public void layoutChildren() {
		super.layoutChildren();
		connections.forEach((String id, ConnectionController cc) -> cc.updateConnection(offset));
	}
}