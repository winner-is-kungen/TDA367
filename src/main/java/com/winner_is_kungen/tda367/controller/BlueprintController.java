package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Blueprint;
import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.util.EventBusEvent;
import com.winner_is_kungen.tda367.view.canvas.InfiniteCanvas;

public class BlueprintController extends InfiniteCanvas {
	private Blueprint blueprint;

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
}