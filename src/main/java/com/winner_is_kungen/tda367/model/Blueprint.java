package com.winner_is_kungen.tda367.model;

import java.util.*;
import java.util.function.BiConsumer;

public class Blueprint {
	/** The list holding all components in this Blueprint. */
	private final List<Component> componentList = new ArrayList<Component>();

	/**
	 * Adds a new component to the Blueprint.
	 * @param component The new component.
	 */
	public void addComponent(Component component) {
		if (componentList.contains(component)) {
			throw new IllegalArgumentException("Can't add a component that's already included in this component.");
		}

		componentList.add(component);
	}

	/**
	 * Gets the Component at the specified index.
	 * @param index The index of the Component.
	 * @return A Component from the Blueprint.
	 */
	public Component getComponent(int index) {
		return componentList.get(index);
	}
	/**
	 * Gets the amount of Components in this Blueprint.
	 * @return The amount of Components in this Blueprint.
	 */
	public int getSize() {
		return componentList.size();
	}

	/**
	 * Removes a component and all of its connections from the Blueprint.
	 * @param component The component to be removed.
	 */
	public void removeComponent(Component component) {
		if (!componentList.contains(component)) {
			throw new IllegalArgumentException("Can't remove a component unless it's included in this Blueprint.");
		}

		removeAllConnections(component);

		componentList.remove(component);
	}

	/**
	 * Connects two components.
	 * @param fromComponent The component the output should be taken from.
	 * @param fromChannel   The channel of the output from the "fromComponent" that should be taken.
	 * @param toComponent   The component that should receive the new input.
	 * @param toChannel     The channel at which the input should be received.
	 */
	public void connect(Component fromComponent, int fromChannel, Component toComponent, int toChannel) {
		if (!componentList.contains(fromComponent) || !componentList.contains(toComponent)) {
			throw new IllegalArgumentException("Can't make a connection between two components unless both are included in this Blueprint.");
		}

		// TODO: Use fromChannel
		fromComponent.addListener(toComponent, toChannel);
	}

	/**
	 * Removes the connection between two components.
	 * @param fromComponent The component the connection went from.
	 * @param fromChannel   The channel the connection went from.
	 * @param toComponent   The component that receives the connection.
	 * @param toChannel     The channel at which the component receives the connection.
	 */
	public void disconnect(Component fromComponent, int fromChannel, Component toComponent, int toChannel) {
		if (!componentList.contains(toComponent)) {
			throw new IllegalArgumentException("Can't handle connections unless the component is included in this Blueprint.");
		}

		// TODO: Use fromChannel
		fromComponent.removeListener(toComponent, toChannel);
	}

	/**
	 * Iterates trough all the listeners that the specified component is listed in.
	 * @param component The listening component.
	 * @param action    The what to do at each step.
	 */
	private void forEachListenerOf(Component component, BiConsumer<Component, Pair<ComponentListener, Integer>> action) {
		for (Component other : componentList) {
			for (int i = 0; i < other.getListenerSize(); i++) {
				Pair<ComponentListener, Integer> listener = other.getListener(i);
				if (listener != null && listener.first().equals(component)) {
					action.accept(other, listener);
				}
			}
		}
	}

	/**
	 * Removes all to and from one component.
	 * @param component The component.
	 */
	public void removeAllConnections(Component component) {
		if (!componentList.contains(component)) {
			throw new IllegalArgumentException("Can't handle connections unless the component is included in this Blueprint.");
		}

		// Removes all connections to this component
		forEachListenerOf(
				component,
				(other, listener) -> {
					other.removeListener(listener.first(), listener.second());
				}
		);

		// Removes all connections from this component
		for (int i = 0; i < component.getListenerSize(); i++) {
			Pair<ComponentListener, Integer> listener = component.getListener(i);
			component.removeListener(listener.first(), listener.second());
		}
	}

	/**
	 * Removes a component from this Blueprint and adds a new component in its place.
	 * Makes sure all connections are added back.
	 * @param oldComponent The component in the Blueprint to be removed.
	 * @param newComponent The new component to be added to the Blueprint.
	 */
	public void replaceComponent(Component oldComponent, Component newComponent) {
		if (!componentList.contains(oldComponent)) {
			throw new IllegalArgumentException("Can't remove a component unless it's included in this Blueprint.");
		}
		if (componentList.contains(newComponent)) {
			throw new IllegalArgumentException("Can't add a component that's already included in this component.");
		}

		// Gather all the connections to the old component
		List<Pair<Component, Integer>> oldInputs = new ArrayList<Pair<Component, Integer>>();
		forEachListenerOf(
				oldComponent,
				(other, listener) -> {
					oldInputs.add(new Pair<Component, Integer>(other, listener.second()));
				}
		);

		// Gather all the connections from the old component
		List<Pair<Component, Integer>> oldOutputs = new ArrayList<Pair<Component, Integer>>();
		for (int i = 0; i < oldComponent.getListenerSize(); i++) {
			Pair<ComponentListener, Integer> listener = oldComponent.getListener(i);
			if (listener.first() instanceof Component) {
				oldOutputs.add(new Pair<Component, Integer>((Component)listener.first(), listener.second()));
			}
		}

		removeComponent(oldComponent);

		addComponent(newComponent);

		// Add all the connections that went to the old component
		for (int i = 0; i < oldInputs.size() && i < newComponent.getNrInputs(); i++) {
			Pair<Component, Integer> incomingConnection = oldInputs.get(i);
			// TODO: Update fromChannel
			connect(incomingConnection.first(), 0, newComponent, incomingConnection.second());
		}

		// Add all the connections that went from the old component
		for (int i = 0; i < oldOutputs.size(); i++) {
			Pair<Component, Integer> outgoingConnection = oldInputs.get(i);
			// TODO: Update fromChannel
			connect(newComponent, 0, outgoingConnection.first(), outgoingConnection.second());
		}
	}
}