package com.winner_is_kungen.tda367.model;
import com.winner_is_kungen.tda367.model.util.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract class for Logic Components to extend from.
 */
public abstract class Component implements ComponentListener{
	private final Position position = new Position();
	private int nrInputs;               // Specifies number of inputs the component has
	private boolean[] inputChannels;    // Stores input values from previous simulations
	private int nrOutputs;              // Specifies number of outputs the component has
	private boolean[] inputFlags;       // Makes sure inputs are only used once.
	private final int id;                     // Identification of node, placeholder
	private String componentTypeID;

	/**
	 * Constructor for the Component
	 * @param id an Integer specifying the given id for the component
	 * @param inputs an Integer specifying the number of inputs the component has
	 */
	public Component(int id, String componentTypeID, int inputs, int outputs) {
		this.nrInputs = inputs;
		this.id = id;
		this.nrOutputs = outputs;
		this.inputChannels = new boolean[nrInputs];
		this.inputFlags = new boolean[nrInputs];
		this.componentTypeID = componentTypeID;

	}

	/**
	 * Gets the Position of this Component.
	 * @return The Position of this Component.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Run to allow the component to receive new updates on all of its inputs
	 */
	void clearInputFlags(){
		Arrays.fill(this.inputFlags,false); // Zeroes out the input_flag
	}

	private List<Tuple<ComponentListener,Integer,Integer>> listeners = new ArrayList<>(); // A list of listeners and their input channel
	/**
	 * Gets the number of incoming channels this component has.
	 * @return The number of incoming channels this component has.
	 */
	public int getNrInputs() {
		return nrInputs;
	}

	/**
	 * Gets the number of outgoing channels this component has.
	 * @return The number of outgoing channels this component has.
	 */
	public int getNrOutputs() {
		return nrOutputs;
	}

	/**
	 * Connects the given input of a component to self output
	 * USAGE: A = Component();
	 *        B = Component();
	 *        A.addListener(B,input_channel);
	 * @param l A object implementing ComponentListener
	 * @param in_channel A Integer specifying which input is used
	 */
	void addListener(ComponentListener l,int in_channel, int out_channel){
		listeners.add(new Tuple<>(l,in_channel,out_channel));
	}

	/**
	 * Disconnects A Component from self
	 * @param l A object implementing ComponentListener
	 * @param in_channel A Integer specifying which input this is connected to
	 */
	void removeListener(ComponentListener l,int in_channel,int out_channel){
		Tuple<ComponentListener,Integer,Integer> p = new Tuple<>(l,in_channel,out_channel);
		listeners.remove(p);
	}

	/**
	 * Gets the listener from this Component at the specified index.
	 * @param index The index of the listener.
	 * @return A listener in this component.
	 */
	Tuple<ComponentListener, Integer, Integer> getListener(int index) {
		return listeners.get(index);
	}

	/**
	 * Gets the amount of listeners this Component updates.
	 * @return The amount of listeners this Component updates.
	 */
	int getListenerSize() {
		return listeners.size();
	}

	protected abstract boolean[] logic(boolean... vars); // Takes an array of booleans and returns a boolean
														// Is to be implemented by the extending class

	/**
	 * Updates the value of the input specified by channel to val.
	 * Also updates the output and sends signals to connected components to update their values
	 * If an input has already received an update it will ignore new updates until clearInputFlags() is called
	 * @param val The new boolean value
	 * @param inChannel A Integer specifying which input
	 */

	public void update(boolean val,int inChannel){
		if(inputFlags[inChannel]) return; // If this component has already received a value into this input, ignore;
											// This stops self calling connections ( See SR Flip ) from causing an infinity loop

		inputFlags[inChannel] = true;     // Sets this input as used until next clearInputFlags()

		inputChannels[inChannel] = val;          // update the specified input
		boolean[] current = logic(inputChannels);    // Evaluate new output
		for (Tuple<ComponentListener, Integer, Integer> p : listeners) { // Broadcast new output to listeners (Components connected to output)
			p.first().update(current[ p.third()], p.second());
		}
	}
}
