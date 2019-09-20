package com.winner_is_kungen.tda367.model;
import java.util.ArrayList;
import java.util.List;

/**
 * Abrstact class for Logic Components to extend from.
 */
public abstract class Component implements ComponentListener{

	private int nrInputs;  // Specifies number of inputs the component has
	private boolean[] inputChannels; // Stores input values from previous simulations

	private int id; // Identification of node, placeholder

	/**
	 * Constructor for the Component
	 * @param id an Integer specifying the given id for the component
	 * @param inputs an Integer specifying the number of inputs the component has
	 */
	public Component(int id,int inputs){
		this.nrInputs = inputs;
		this.id = id;

		this.inputChannels = new boolean[nrInputs];
	}

	private List<Pair<ComponentListener,Integer>> listeners = new ArrayList<>(); // A list of listeners and their input channel

	/**
	 * Connects the given input of a component to self output
	 * USAGE: A = Component();
	 *        B = Component();
	 *        A.addListener(B,input_channel);
	 * @param l A object implementing ComponentListener
	 * @param channel A Integer specifying which input is used
	 */
	void addListener(ComponentListener l,int channel){
		listeners.add(new Pair<>(l,channel));
	}

	/**
	 * Disconnects A Component from self
	 * @param l A object implementing ComponentListener
	 * @param channel A Integer specifying which input this is connected to
	 */
	void removeListener(ComponentListener l,int channel){
		Pair<ComponentListener,Integer> p = new Pair<>(l,channel);
		listeners.remove(p);
	}

	protected abstract boolean logic(boolean... vars); // Takes an array of booleans and returns a boolean
														// Is to be implemented by the extending class

	/**
	 * Updates the value of the input specified by channel to val.
	 * Also updates the output and sends signals to connected components to update their values
	 * @param val The new boolean value
	 * @param channel A Integer specifying which input
	 */
	public void update(boolean val,int channel){
		inputChannels[channel] = val;              // update the specified input
		boolean current = logic(inputChannels);    // Evaluate new output
		for (Pair p :listeners) {                   // Broadcast new output to listeners (Components connected to output)
			((ComponentListener)(p.first())).update(current,(int) p.second());
		}
	}
}
