package com.winner_is_kungen.tda367.model.electricalComponents;

import com.winner_is_kungen.tda367.model.ElectricalComponent;

public class And extends ElectricalComponent {
	public final static String typeId = "and";

	/** The amount of inputs this instance can take. */
	private final int inputSize;

	/**
	 * Creates an and-gate.
	 * @param id        A unique id for this instance.
	 * @param inputSize The amount of inputs this instance should take.
	 */
	public And(String id, int inputSize) {
		super(id, typeId, inputSize, 1);
		this.inputSize = inputSize;
		setOutput(0, this::output);
	}

	/**
	 * This method calculates the output of an and-gate.
	 * @return The and'ed value between all of the inputs.
	 */
	private boolean output() {
		for (int i = 0; i < inputSize; i++) {
			if (!getInputValue(i)) {
				return false;
			}
		}

		return true;
	}
}