package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Blueprint;
import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.util.EventBusEvent;
import com.winner_is_kungen.tda367.view.canvas.InfiniteCanvas;
import com.winner_is_kungen.tda367.controller.ConnectionPointController.ConnectionPointType;
import javafx.geometry.Point2D;

import java.util.HashMap;
public class BlueprintController extends InfiniteCanvas {
	private Blueprint blueprint;

	private ConnectionPointController connectionStart;
	private boolean connectionInProgress = false;

	private HashMap<String,ComponentController> componentControllers = new HashMap<>();
	private HashMap<String, ConnectionController> connections = new HashMap<>();
	
	/**
	 * Sets which Blueprint this controller should display and interact with.
	 */
	public void setBlueprint(Blueprint blueprint) {
		if (this.blueprint != null) {
			this.blueprint.getEventBus().removeListener(Blueprint.eventComponent, this::onComponentChange);
		}

		this.blueprint = blueprint;

		getChildren().clear();

		if (this.blueprint != null) {
			for (int i = 0; i < this.blueprint.getSize(); i++) {
				getChildren().add(ComponentControllerFactory.Create(this,this.blueprint.getComponent(i)));
			}

			this.blueprint.getEventBus().addListener(Blueprint.eventComponent, this::onComponentChange);
			this.blueprint.getEventBus().addListener(Blueprint.eventConnection, this::onConnectionChange);
		}
	}

	public void startConnection(ConnectionPointController c1){
		if(connectionInProgress) {
			completeConnection(c1);
			connectionInProgress = false;
		}
		else {
			connectionStart = c1;
			connectionInProgress = true;
		}
	}

	private void completeConnection(ConnectionPointController connectionEnd){
		if(connectionStart == connectionEnd) return;                   // Do not create connection between the same point
		if(connectionStart.ioType == connectionEnd.ioType) return;   // Do not connect an input to an input and vice versa

		Component c1;
		Component c2;
		int inputChannel;
		int outputChannel;

		// Allow connections of any order ( input - output | output - input)

		if(connectionStart.ioType == ConnectionPointType.INPUT){
			c1 = connectionEnd.component.getModel();
			c2 = connectionStart.component.getModel();

			outputChannel = connectionEnd.channel;
			inputChannel = connectionStart.channel;
		}
		else{
			c1 = connectionStart.component.getModel();
			c2 = connectionEnd.component.getModel();

			outputChannel = connectionStart.channel;
			inputChannel = connectionEnd.channel;
		}

		blueprint.connect(c1,outputChannel,c2,inputChannel);
		// If the model accepts the connection it will broadcast back with an event to create the connection
	}

	/**
	 * Adds a new component to this controllers Blueprint.
	 * @param component The component to be added.
	 */
	public void addComponent(Component component) {
		if (blueprint != null) {
			blueprint.addComponent(component);
		}
		else {
			throw new IllegalStateException("Must set a Blueprint first.");
		}
	}

	/**
	 * Removes a component from this controllers Blueprint.
	 * @param component The component to be removed.
	 */
	public void removeComponent(Component component) {
		if (blueprint != null) {
			blueprint.removeComponent(component);
		}
		else {
			throw new IllegalStateException("Must set a Blueprint first.");
		}
	}

	/**
	 * Reacts to Component changes in the Blueprint.
	 * Such as adding or removing Components.
	 * @param event Event message object.
	 */
	private void onComponentChange(EventBusEvent<Blueprint.ComponentEvent> event) {
		if (event.getMessage().isAdded()) {
			ComponentController newComponent = ComponentControllerFactory.Create(this,event.getMessage().getAffectedComponent());
			getChildren().add(newComponent);
			componentControllers.put(newComponent.getID(),newComponent);
		}
		else {
			componentControllers.remove(event.getMessage().getAffectedComponent().getId());
			getChildren().removeIf(
					x -> x instanceof ComponentController && ((ComponentController)x).getModel() == event.getMessage().getAffectedComponent()
			);
		}
	}

	private void onConnectionChange(EventBusEvent<Blueprint.ConnectionEvent> event){
		System.out.println("Recieving connection event");
		Blueprint.ConnectionEvent msg = event.getMessage();
		if(msg.isConnected()){
			createConnection(msg.getFromComponent(),msg.getOutChannel(),msg.getInChannel(),msg.getToComponent());
		}
		else{
			removeConnection(msg.getFromComponent(),msg.getOutChannel(),msg.getInChannel(),msg.getToComponent());
		}
	}

	private void createConnection(Component fromComponent, int outChannel, int inChannel, Component toComponent) {
		ComponentController fromCC = componentControllers.get(fromComponent.getId());
		ComponentController toCC = componentControllers.get(toComponent.getId());

		System.out.println("Creating connection");

		ConnectionPointController fromCP = fromCC.getOutputConnectionPoint(outChannel);
		ConnectionPointController toCP = toCC.getInputConnectionPoint(inChannel);

		String lineID = fromCC.getID() + ":" + String.valueOf(outChannel) + "->"+ String.valueOf(inChannel)+":" + toCC.getID();

		ConnectionController connection = new ConnectionController(this,fromCP,toCP);

		connections.put(lineID,connection);
		this.getChildren().add(connection);

	}

	private void removeConnection(Component fromComponent, int outChannel, int inChannel,Component toComponent){
		ComponentController fromCC = componentControllers.get(fromComponent.getId());
		ComponentController toCC = componentControllers.get(toComponent.getId());

		String lineID = fromCC.getID() + ":" + String.valueOf(outChannel) + "->"+ String.valueOf(inChannel)+":" + toCC.getID();

		ConnectionController toBeRemoved = connections.remove(lineID);
		this.getChildren().remove(toBeRemoved);
	}

	@Override
	public void layoutChildren() {
		super.layoutChildren();
		connections.forEach((String id,ConnectionController cc) -> cc.updateConnection());
	}

	public Point2D getOffset(){
		return offset;
	}
}