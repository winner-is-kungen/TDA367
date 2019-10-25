package com.winner_is_kungen.tda367.model;

import java.util.List;

/**
 * An interface for receiving updates from components
 */
public interface ComponentListener {
	/**
	 * When connected to another component, it will be called when the value of the component changes
	 * @param updateID   The id for the current update
	 * @param val        The new value from output
	 * @param in_channel The target input channel
	 */
	void update(List<ComponentUpdateRecord> updateRecords, boolean val, int in_channel);
}
