package com.winner_is_kungen.tda367.model.util;

import com.winner_is_kungen.tda367.model.ComponentListener;

/**
	A container for connections between components.
    Each record contains the listener, the channel it's listening to,
    and the input channel where the listener wants new values delivered to.

    the ConnectionRecord is final once set.
 */

public class ConnectionRecord<L extends ComponentListener> {
	private final L listener;
	private final int outputChannel;
	private final int inputChannel;

	/**
	 * A Constructor for a Pair
	 *
	 * @param listener  a object of type F
	 * @param outputChannel a object of type S
	 * @param inputChannel  a object of type T
	 */
	public ConnectionRecord(L listener, Integer inputChannel, Integer outputChannel) {
		this.listener = listener;
		this.outputChannel = outputChannel;
		this.inputChannel = inputChannel;
	}

	/**
	 * A getter for the ComponentListener
	 *
	 * @return returns the componentListener
	 */
	public L getListener() {
		return listener;
	}

	/**
	 * A getter for the output channel the listener is listening to
	 *
	 * @return returns an integer
	 */
	public int getInputChannel() {
		return inputChannel;
	}

	/**
	 * A getter for the destination input channel
	 *
	 * @return returns an integer
	 */
	public int getOutputChannel() {
		return outputChannel;
	}

	/**
	 * A function for comparing two ConnectionRecords
	 *
	 * @param obj a object to compare with self
	 * @return returns true if obj is a Pair and the first and second obj are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;                        // True if compared with self
		if (null == obj) return false;                       // False if compared with nothing
		if (this.getClass() != obj.getClass()) return false; // False if obj is not a Pair
		ConnectionRecord p = (ConnectionRecord) obj;         // True if both components of the two Pairs are equal
		return this.getListener() == p.getListener() && this.getInputChannel() == p.getInputChannel() && this.getOutputChannel() == p.getOutputChannel();
	}
}
