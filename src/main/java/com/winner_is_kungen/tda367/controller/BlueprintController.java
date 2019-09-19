package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.view.canvas.InfiniteCanvas;

public class BlueprintController extends InfiniteCanvas {
	/*private Blueprint blueprint;*/

	/**
	 * Sets which Blueprint this controller should display and interact with.
	 */
	public void setBlueprint(/*Blueprint blueprint*/) {
		/*this.blueprint = blueprint;*/

		getManagedChildren().clear();
		/*for (ElectricalComponent component : this.blueprint.components) {
			getManagedChildren().add(ElectricalComponentControllerFactory.Create(component));
		}*/
	}

	/**
	 * Adds a new component to this controllers Blueprint.
	 * @param component The component to be added.
	 */
	public void addComponent(/*Electrical component*/) {
		getManagedChildren().add(ElectricalComponentControllerFactory.Create(/*component*/));
		/*blueprint.addComponent(component);*/
	}
}