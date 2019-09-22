package com.winner_is_kungen.tda367.model.electricalComponents;

import com.winner_is_kungen.tda367.model.ElectricalComponent;

public class Not extends ElectricalComponent {
	public final static String typeId = "not";

	/**
	 * Creates a basic not-gate.
	 * @param id A unique id for this instance.
	 */
	public Not(String id) {
		super(id, typeId, 1, 1);
		setOutput(0, this::output);
	}

	/**
	 * This method calculates the output of a not-gate.
	 * @return The negated value of the input.
	 */
	private boolean output() {
		return !getInputValue(0);
	}
}