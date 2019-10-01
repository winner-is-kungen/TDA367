package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Blueprint;
import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.view.canvas.InfiniteCanvas;

public class BlueprintController extends InfiniteCanvas {
	private Blueprint blueprint;

	/**
	 * Sets which Blueprint this controller should display and interact with.
	 */
	public void setBlueprint(Blueprint blueprint) {
		this.blueprint = blueprint;

		getChildren().clear();

		if (blueprint != null) {
			for (int i = 0; i < blueprint.getSize(); i++) {
				getChildren().add(ComponentControllerFactory.Create(blueprint.getComponent(i)));
			}
		}
	}

	/**
	 * Adds a new component to this controllers Blueprint.
	 * @param component The component to be added.
	 */
	public void addComponent(Component component) {
		getChildren().add(ComponentControllerFactory.Create(component));
		blueprint.addComponent(component);
	}
}