package com.winner_is_kungen.tda367.model;


/**
 * Placeholder output for debug purposes
 * Usage: 1.Connect to a desired component
 *         2. Query for updated values with getChannel(channel);
 */
public class Output implements ComponentListener {

	private boolean channels[]; // Storage for inputs of the component;

	/**
	 * Constructor for Output
	 * @param inputs An integer specifying number of inputs the component can handle
	 */
	public Output(int inputs){
		channels = new boolean[inputs];
	}

	/**
	 * Called by Components the component is listening to
	 * @param val   The new value from output
	 * @param channel The target input channel
	 */
	public void update(boolean val, int channel){
		channels[channel] = val;
		//System.out.println("Output on channel"+channel+"is "+val);
	}

	/**
	 * A getter for the most recent boolean value of the specified input.
	 * @param channel the specified input
	 * @return A boolean specifying the value of the specified input. If no calls have been mad to update the return is undefined
	 */
	public boolean getChannel(int channel){
		return channels[channel];
	}
}
