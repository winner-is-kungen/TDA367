package com.winner_is_kungen.tda367.model;

/**
 *  An interface for receiving updates from components
 */
public interface ComponentListener{
	/**
	 * When connected to another component, it will be called when the value of the component changes
	 * @param val   The new value from output
	 * @param channel The target input
	 */
	void update(boolean val, int channel);
}
