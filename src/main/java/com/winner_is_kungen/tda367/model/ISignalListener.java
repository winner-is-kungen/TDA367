package com.winner_is_kungen.tda367.model;

/**
 * A functional interface for receiving updates from Signals.
 */
public interface ISignalListener {
	/**
	 * Implement this method to to receive updates.
	 * @param id The id of the received update.
	 */
	void update(String id);
}