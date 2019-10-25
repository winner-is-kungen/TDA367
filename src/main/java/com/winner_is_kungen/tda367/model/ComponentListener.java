package com.winner_is_kungen.tda367.model;

import java.util.List;

/**
 * An interface for receiving updates from components
 */
public interface ComponentListener {
	/**
	 * When connected to another component, it will be called when the value of the component changes
	 * @param updateRecords A list of all previously encountered component and channel combinations.
	 * @param val           The new value from output
	 * @param inChannel     The target input channel
	 */
	void update(List<ComponentUpdateRecord> updateRecords, boolean val, int inChannel);

	default void update(boolean val, int inChannel) {
		update(List.of(), val, inChannel);
	}

	interface ComponentListenerNoRecords extends ComponentListener {
		@Override
		default void update(List<ComponentUpdateRecord> updateRecords, boolean val, int inChannel) {
			update(val, inChannel);
		}

		@Override
		void update(boolean val, int inChannel);
	}
}
