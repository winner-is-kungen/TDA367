package com.winner_is_kungen.tda367.model;

import java.util.*;

/**
 * A base class for all ElectricalComponents with position, inputs, and outputs.
 */
public abstract class ElectricalComponent {
	/** A unique id of this instance. */
	private final String id;
	/** An id for this type of components. */
	private final String typeId;

	/** An array of all output Signals. Must be set in the constructor of implementing classes. */
	private final Signal[] outputs;
	/** An array of input Signals. Can be updated whenever. */
	private final Signal[] inputs;

	/** The id of the last update to this component. */
	private String previousUpdateId = null;

	/**
	 * Creates a new ElectricalComponent.
	 * @param id         A unique id of this instance.
	 * @param typeId     An id for this type of components.
	 * @param inputSize  The amount of inputs this component takes.
	 * @param outputSize The amount of outputs this component gives.
	 */
	public ElectricalComponent(String id, String typeId, int inputSize, int outputSize) {
		this.id = id;
		this.typeId = typeId;
		this.inputs = new Signal[inputSize];
		this.outputs = new Signal[outputSize];
	}

	/**
	 * Gets the unique id of this instance.
	 * @return The unique id of this instance.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the id for this type of components.
	 * @return The id for this type of components.
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * Sets a Signal as the input at the specified index.
	 * Sends an update to all outputs if a change occurred.
	 * @param index The input index.
	 * @param input The Signal that should be the new input. Can be null to remove this input.
	 */
	public void setInput(int index, Signal input) {
		Signal oldInput = inputs[index];

		// Removes this components listener from the old input Signal, if one was set.
		if (oldInput != null) {
			oldInput.removeListener(getId(), index);
		}
		// Adds this components listener to the new Signal, if one is available.
		if (input != null && oldInput != input) {
			inputs[index] = input;
			input.addListener(new ComponentListener(getId(), index, this::update));
		}

		// Sends out an update if the old and the new Signals differ.
		if (oldInput != input) {
			update(UUID.randomUUID().toString());
		}
	}

	/**
	 * Gets the value from the Signal at the specified index.
	 * @param index The index of the chosen input.
	 * @return The value of the specified Signal, or `false` if it's unavailable.
	 */
	protected boolean getInputValue(int index) {
		return inputs[index] != null ? inputs[index].getValue() : false;
	}

	/**
	 * Gets an immutable collection of this components current inputs.
	 * @return An immutable collection of this components current inputs.
	 */
	public List<Signal> getInputs() {
		return List.of(inputs);
	}

	/**
	 * Creates a new Signal to be used as output at the specified index.
	 * @param index  The index of the chosen output.
	 * @param output The method to be used as value getter in the new Signal.
	 */
	protected void setOutput(int index, ISignalValue output) {
		outputs[index] = new Signal(getId(), index, output);
	}

	/**
	 * Gets the Signal at the specified index.
	 * @param index The index of the chosen output Signal.
	 * @return The Signal at the specified index.
	 */
	public Signal getOutput(int index) {
		return outputs[index];
	}

	/**
	 * Relays an update to all of this components output Signals.
	 * Unless this component already has received an update with this id.
	 * @param id The id of the update.
	 */
	private void update(String id) {
		if (id == null) {
			throw new IllegalArgumentException("id can't be null");
		}

		if (!id.equals(previousUpdateId)) {
			previousUpdateId = id;

			for (Signal output : outputs) {
				output.update(id);
			}
		}
	}
}