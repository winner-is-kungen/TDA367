package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Blueprint;
import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.util.EventBusEvent;
import com.winner_is_kungen.tda367.view.canvas.InfiniteCanvas;
import com.winner_is_kungen.tda367.controller.ConnectionPointController.ConnectionPointType;

public class BlueprintController extends InfiniteCanvas {
	private Blueprint blueprint;

	private ConnectionPointController connectionStart;
	private boolean connectionInProgress = false;

	public BlueprintController(){
		ComponentController comp = ComponentControllerFactory.Create(new AndGate(1));
		this.getChildren().add(comp);
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

		if (this.blueprint != null) {
			for (int i = 0; i < this.blueprint.getSize(); i++) {
				getChildren().add(ComponentControllerFactory.Create(this.blueprint.getComponent(i)));
			}

			this.blueprint.getEventBus().addListener(Blueprint.eventComponent, this::onComponentChange);
		}
	}

	public void startConnection(ConnectionPointController c1){
		if(connectionInProgress) {
			completeConnection(c1);
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
			getChildren().add(ComponentControllerFactory.Create(event.getMessage().getAffectedComponent()));
		}
		else {
			getChildren().removeIf(
					x -> x instanceof ComponentController && ((ComponentController)x).getModel() == event.getMessage().getAffectedComponent()
			);
		}
	}

	private void onConnectionChange(EventBusEvent<Blueprint.ConnectionEvent> event){
		if(event.getMessage().isConnected()){
			// Create line to components
		}
		else{
			// Remove line between components
		}
	}
}