package com.winner_is_kungen.tda367.model;

import java.util.*;

public class Blueprint {
	/** The list holding all components in this Blueprint. */
	private final List<ElectricalComponent> componentList = new ArrayList<ElectricalComponent>();

	/**
	 * Adds a new component to the Blueprint.
	 * @param component The new component.
	 */
	public void addComponent(ElectricalComponent component) {
		if (componentList.contains(component)) {
			throw new IllegalArgumentException("Can't add a component that's already included in this component.");
		}

		componentList.add(component);
	}

	/**
	 * Removes a component and all of its connections from the Blueprint.
	 * @param component The component to be removed.
	 */
	public void removeComponent(ElectricalComponent component) {
		if (!componentList.contains(component)) {
			throw new IllegalArgumentException("Can't remove a component unless it's included in this Blueprint.");
		}

		removeAllConnections(component);

		componentList.remove(component);
	}

	/**
	 * Connects two components.
	 * @param fromComponent The component the output should be taken from.
	 * @param fromIndex     The index of the output from the "fromComponent" that should be taken.
	 * @param toComponent   The component that should receive the new input.
	 * @param toIndex       The index at which the input should be received.
	 */
	public void connect(ElectricalComponent fromComponent, int fromIndex, ElectricalComponent toComponent, int toIndex) {
		if (!componentList.contains(fromComponent) || !componentList.contains(toComponent)) {
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
		if (!componentList.contains(toComponent)) {
			throw new IllegalArgumentException("Can't handle connections unless the component is included in this Blueprint.");
		}

		toComponent.setInput(toIndex, null);
	}

	/**
	 * Removes all to and from one component.
	 * @param component The component.
	 */
	private void removeAllConnections(ElectricalComponent component) {
		if (!componentList.contains(component)) {
			throw new IllegalArgumentException("Can't handle connections unless the component is included in this Blueprint.");
		}

		// Removes all inputs from this component.
		for (int i = 0; i < component.getInputSize(); i++) {
			component.setInput(i, null);
		}

		// Removes all inputs in other components that com from this component.
		for (ElectricalComponent other : componentList) {
			for (int i = 0; i < other.getInputSize(); i++) {
				Signal input = other.getInput(i);
				if (input != null && input.getOwnerId().equals(component.getId())) {
					other.setInput(i, null);
				}
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
		if (!componentList.contains(oldComponent)) {
			throw new IllegalArgumentException("Can't remove a component unless it's included in this Blueprint.");
		}
		if (componentList.contains(newComponent)) {
			throw new IllegalArgumentException("Can't add a component that's already included in this component.");
		}

		// Gather all of the input connections the old component has
		List<Signal> oldInputs = new ArrayList<Signal>(oldComponent.getInputSize());
		for (int i = 0; i < oldComponent.getInputSize(); i++) {
			oldInputs.add(oldComponent.getInput(i));
		}

		// Gather all of the output connections the old component has
		List<List<Map.Entry<Integer, ElectricalComponent>>> oldOutputs = new ArrayList<List<Map.Entry<Integer, ElectricalComponent>>>(oldComponent.getOutputSize());
		for (int i = 0; i < oldComponent.getOutputSize(); i++) {
			oldOutputs.add(new ArrayList<Map.Entry<Integer, ElectricalComponent>>());
		}
		for (ElectricalComponent other : componentList) {
			for (int i = 0; i < other.getInputSize(); i++) {
				Signal input = other.getInput(i);
				if (input != null && input.getOwnerId().equals(oldComponent.getId())) {
					oldOutputs.get(input.getIndex()).add(new AbstractMap.SimpleEntry<Integer, ElectricalComponent>(i, other));
				}
			}
		}

		removeComponent(oldComponent);

		addComponent(newComponent);

		// Add all the inputs from the old component
		for (int i = 0; i < oldInputs.size() && i < newComponent.getInputSize(); i++) {
			newComponent.setInput(i, oldInputs.get(i));
		}

		// Add all outputs to other components that the old component had
		for (int i = 0; i < oldOutputs.size() && i < newComponent.getOutputSize(); i++) {
			for (int j = 0; j < oldOutputs.get(i).size(); j++) {
				oldOutputs.get(i).get(j).getValue().setInput(oldOutputs.get(i).get(j).getKey(), newComponent.getOutput(i));
			}
		}
	}
}