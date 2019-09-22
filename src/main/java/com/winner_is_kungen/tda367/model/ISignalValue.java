package com.winner_is_kungen.tda367.model;

/**
 * A functional interface for giving out the value of a Signal.
 * A ElectricalComponent should implement this an based on its inputs calculate what to return as the Signals output.
 */
public interface ISignalValue {
	/**
	 * This is the method for getting the value of Signal.
	 * @return The calculated value of a Signal.
	 */
	boolean getValue();
}