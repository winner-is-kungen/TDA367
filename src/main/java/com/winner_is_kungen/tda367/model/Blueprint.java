package com.winner_is_kungen.tda367.model;

import java.util.List;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class Blueprint implements SimulationManager{
	/** The list holding all components in this Blueprint. */
	private final List<Component> componentList = new ArrayList<Component>();
	private int currentSimulationID = 0;


	 void incSimulationID(){
		currentSimulationID += 1;
		if(currentSimulationID == Integer.MAX_VALUE) currentSimulationID = 0;
	}

	public int getSimulationID(){
		return currentSimulationID;
	}

	/**
	 * Adds a new component to the Blueprint.
	 * @param component The new component.
	 */
	public void addComponent(Component component) {
		if (componentList.contains(component)) {
			throw new IllegalArgumentException("Can't add a component that's already included in this component.");
		}
		component.setSimulationManager(this);
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
	 * @param outChannel    The channel of the output from the "fromComponent" that should be taken.
	 * @param toComponent   The component that should receive the new input.
	 * @param inChannel     The channel at which the input should be received.
	 */
	public void connect(Component fromComponent, int outChannel, Component toComponent, int inChannel) {
		if (!componentList.contains(fromComponent) || !componentList.contains(toComponent)) {
			throw new IllegalArgumentException("Can't make a connection between two components unless both are included in this Blueprint.");
		}

		if (fromComponent.getNrOutputs() <= outChannel) {
			throw new IllegalArgumentException("Out channel out of range.");
		}
		if (toComponent.getNrInputs() <= inChannel) {
			throw new IllegalArgumentException("In channel out of range.");
		}

		forEachListenerOf(
				toComponent,
				(other, listener) -> {
					if (listener.second() == inChannel) {
						throw new IllegalStateException("One component can't have two inputs on the same channel.");
					}
				}
		);

		fromComponent.addListener(toComponent, inChannel, outChannel);
	}

	/**
	 * Removes the connection between two components.
	 * @param fromComponent The component the connection went from.
	 * @param outChannel    The channel the connection went from.
	 * @param toComponent   The component that receives the connection.
	 * @param inChannel     The channel at which the component receives the connection.
	 */
	public void disconnect(Component fromComponent, int outChannel, Component toComponent, int inChannel) {
		if (!componentList.contains(toComponent)) {
			throw new IllegalArgumentException("Can't handle connections unless the component is included in this Blueprint.");
		}

		fromComponent.removeListener(toComponent, inChannel, outChannel);
	}

	/**
	 * Iterates trough all the listeners that the specified component is listed in.
	 * @param component The listening component.
	 * @param action    What to do at each step.
	 */
	private void forEachListenerOf(Component component, BiConsumer<Component, Tupple<ComponentListener, Integer, Integer>> action) {
		for (Component other : componentList) {
			for (int i = 0; i < other.getListenerSize(); i++) {
				Tupple<ComponentListener, Integer, Integer> listener = other.getListener(i);
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
					other.removeListener(listener.first(), listener.second(), listener.third());
				}
		);

		// Removes all connections from this component
		for (int i = 0; i < component.getListenerSize(); i++) {
			Tupple<ComponentListener, Integer, Integer> listener = component.getListener(i);
			component.removeListener(listener.first(), listener.second(), listener.third());
		}
	}

	/**
	 * Removes a component from this Blueprint and adds a new component in its place.
	 * Makes sure all possible connections are added back.
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
		List<Tupple<Component, Integer, Integer>> oldInputs = new ArrayList<Tupple<Component, Integer, Integer>>();
		forEachListenerOf(
				oldComponent,
				(other, listener) -> {
					oldInputs.add(new Tupple<Component, Integer, Integer>(other, listener.second(), listener.third()));
				}
		);

		// Gather all the connections from the old component
		List<Tupple<Component, Integer, Integer>> oldOutputs = new ArrayList<Tupple<Component, Integer, Integer>>();
		for (int i = 0; i < oldComponent.getListenerSize(); i++) {
			Tupple<ComponentListener, Integer, Integer> listener = oldComponent.getListener(i);
			if (listener.first() instanceof Component) {
				oldOutputs.add(new Tupple<Component, Integer, Integer>((Component)listener.first(), listener.second(), listener.third()));
			}
		}

		removeComponent(oldComponent);

		addComponent(newComponent);

		// Add all the connections that went to the old component
		for (int i = 0; i < oldInputs.size(); i++) {
			Tupple<Component, Integer, Integer> incomingConnection = oldInputs.get(i);
			if (newComponent.getNrInputs() > incomingConnection.second()) {
				connect(incomingConnection.first(), incomingConnection.third(), newComponent, incomingConnection.second());
			}
		}

		// Add all the connections that went from the old component
		for (int i = 0; i < oldOutputs.size(); i++) {
			Tupple<Component, Integer, Integer> outgoingConnection = oldOutputs.get(i);
			if (newComponent.getNrOutputs() > outgoingConnection.third()) {
				connect(newComponent, outgoingConnection.third(), outgoingConnection.first(), outgoingConnection.second());
			}
		}
	}
}