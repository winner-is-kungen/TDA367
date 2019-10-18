package com.winner_is_kungen.tda367.model;

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
	void update(String updateID,boolean val, int in_channel);
}
