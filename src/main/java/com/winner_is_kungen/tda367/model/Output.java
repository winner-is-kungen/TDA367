package com.winner_is_kungen.tda367.model;


/**
 * Placeholder output for debug purposes
 * Usage: 1.Connect to a desired component
 *         2. Query for updated values with getChannel(channel);
 */
public class Output extends Component {

	private boolean channels[]; // Storage for inputs of the component;

	/**
	 * Constructor for Output
	 * @param inputs An integer specifying number of inputs the component can handle
	 */
	public Output(int id, int inputs){
		super(id, inputs);
	}

	/**
	 * A getter for the most recent boolean value of the specified input.
	 * @param channel the specified input
	 * @return A boolean specifying the value of the specified input. If no calls have been mad to update the return is undefined
	 */
	public boolean getChannel(int channel){
		return channels[channel];
	}

	/**
	 * Used for reading the inputs.
	 * @param vars The inputs.
	 * @return An empty boolean array.
	 */
	@Override
	protected boolean[] logic(boolean... vars) {
		channels = vars;
		return new boolean[0];
	}
}
