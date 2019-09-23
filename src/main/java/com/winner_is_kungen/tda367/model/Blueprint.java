package com.winner_is_kungen.tda367.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Blueprint {
	/**
	 * The map holding all components in this Blueprint.
	 * Mapping from component ids to components.
	 */
	private final Map<String, ElectricalComponent> componentList = new HashMap<String, ElectricalComponent>();

	/**
	 * Adds a new component to the Blueprint.
	 * @param component The new component.
	 */
	public void addComponent(ElectricalComponent component) {
		if (componentList.containsKey(component.getId())) {
			throw new IllegalArgumentException("Can't add a component that's already included in this component.");
		}

		componentList.put(component.getId(), component);
	}

	/**
	 * Removes a component and all of its connections from the Blueprint.
	 * @param component The component to be removed.
	 */
	public void removeComponent(ElectricalComponent component) {
		if (!componentList.containsKey(component.getId())) {
			throw new IllegalArgumentException("Can't remove a component unless it's included in this Blueprint.");
		}

		removeAllConnections(component);

		componentList.remove(component.getId());
	}

	/**
	 * Connects two components.
	 * @param fromComponent The component the output should be taken from.
	 * @param fromIndex     The index of the output from the "fromComponent" that should be taken.
	 * @param toComponent   The component that should receive the new input.
	 * @param toIndex       The index at which the input should be received.
	 */
	public void connect(ElectricalComponent fromComponent, int fromIndex, ElectricalComponent toComponent, int toIndex) {
		if (!componentList.containsKey(fromComponent.getId()) || !componentList.containsKey(toComponent.getId())) {
			throw new IllegalArgumentException("Can't make a connection between two components unless both are included in this Blueprint.");
		}

		toComponent.setInput(toIndex, fromComponent.getOutput(fromIndex));
	}

	/**
	 * Removes the connection between two components.
	 * @param toComponent The component that receives the connection.
	 * @param toIndex     The index at which the component receives the connection.
	 */
	public void disconnect(ElectricalComponent toComponent, int toIndex) {
		if (!componentList.containsKey(toComponent.getId())) {
			throw new IllegalArgumentException("Can't handle connections unless the component is included in this Blueprint.");
		}

		toComponent.setInput(toIndex, null);
	}

	/**
	 * Removes all to and from one component.
	 * @param component The component.
	 */
	private void removeAllConnections(ElectricalComponent component) {
		if (!componentList.containsKey(component.getId())) {
			throw new IllegalArgumentException("Can't handle connections unless the component is included in this Blueprint.");
		}

		// Removes all inputs from this component.
		for (int i = 0; i < component.getInputSize(); i++) {
			component.setInput(i, null);
		}

		// Removes all inputs in other components that com from this component.
		for (int i = 0; i < component.getOutputSize(); i++) {
			Signal outSignal = component.getOutput(i);
			for (ComponentListener componentListener : outSignal.getComponentListeners()) {
				disconnect(componentList.get(componentListener.getComponentId()), componentListener.getIndex());
			}
		}
	}

	/**
	 * Removes a component from this Blueprint and adds a new component in its place.
	 * Makes sure all connections are added back.
	 * @param oldComponent The component in the Blueprint to be removed.
	 * @param newComponent The new component to be added to the Blueprint.
	 */
	public void replaceComponent(ElectricalComponent oldComponent, ElectricalComponent newComponent) {
		if (!componentList.containsKey(oldComponent.getId())) {
			throw new IllegalArgumentException("Can't remove a component unless it's included in this Blueprint.");
		}
		if (componentList.containsKey(newComponent.getId())) {
			throw new IllegalArgumentException("Can't add a component that's already included in this component.");
		}

		// Gather all of the input connections the old component has
		List<Signal> oldInputs = new ArrayList<Signal>(oldComponent.getInputSize());
		for (int i = 0; i < oldComponent.getInputSize(); i++) {
			oldInputs.add(oldComponent.getInput(i));
		}

		// Gather all of the output connections the old component has
		List<List<ComponentListener>> oldOutputs = new ArrayList<List<ComponentListener>>();
		for (int i = 0; i < oldComponent.getOutputSize(); i++) {
			oldOutputs.add(oldComponent.getOutput(i).getComponentListeners());
		}

		removeComponent(oldComponent);

		addComponent(newComponent);

		// Add all the inputs from the old component
		for (int i = 0; i < oldInputs.size() && i < newComponent.getInputSize(); i++) {
			Signal oldSignal = oldInputs.get(i);
			connect(componentList.get(oldSignal.getOwnerId()), oldSignal.getIndex(), newComponent, i);
		}

		// Add all outputs to other components that the old component had
		for (int i = 0; i < oldOutputs.size() && i < newComponent.getOutputSize(); i++) {
			List<ComponentListener> oldComponentListeners = oldOutputs.get(i);
			for (ComponentListener oldListener : oldComponentListeners) {
				connect(newComponent, i, componentList.get(oldListener.getComponentId()), oldListener.getIndex());
			}
		}
	}
}