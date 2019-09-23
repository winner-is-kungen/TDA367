package com.winner_is_kungen.tda367.model.electricalComponents;

import com.winner_is_kungen.tda367.model.ElectricalComponent;

import java.util.UUID;

public class Switch extends ElectricalComponent {
	public final static String typeId = "switch";

	private boolean value = false;

	/**
	 * Creates a new Switch component.
	 * @param id A unique id of this instance.
	 */
	public Switch(String id) {
		super(id, typeId, 0, 1);
		setOutput(0, this::getValue);
	}

	/**
	 * Gets the value of this Switch.
	 * @return The value of this Switch.
	 */
	public boolean getValue() {
		return value;
	}
	/**
	 * Sets the value of this Switch.
	 * @param value The new value for this Switch.
	 */
	public void setValue(boolean value) {
		this.value = value;

		update(UUID.randomUUID().toString());
	}
}