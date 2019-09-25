package com.winner_is_kungen.tda367.model;
import java.util.ArrayList;
import java.util.List;

/**
 * Abrstact class for Logic Components to extend from.
 */
public abstract class Component implements ComponentListener{

	private int nrInputs;  // Specifies number of inputs the component has
	private boolean[] inputChannels; // Stores input values from previous simulations
	private int nrOutputs;  // Specifies number of outputs the component has

	private int id; // Identification of node, placeholder

	/**
	 * Constructor for the Component
	 * @param id an Integer specifying the given id for the component
	 * @param inputs an Integer specifying the number of inputs the component has
	 */
	public Component(int id, int inputs, int outputs){
		this.nrInputs = inputs;
		this.nrOutputs = outputs;
		this.id = id;

		this.inputChannels = new boolean[nrInputs];
	}

	private List<Tupple<ComponentListener,Integer,Integer>> listeners = new ArrayList<>(); // A list of listeners and their input channel
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
		listeners.add(new Tupple<>(l,in_channel,out_channel));
	}

	/**
	 * Disconnects A Component from self
	 * @param l A object implementing ComponentListener
	 * @param in_channel A Integer specifying which input this is connected to
	 */
	void removeListener(ComponentListener l,int in_channel,int out_channel){
		Tupple<ComponentListener,Integer,Integer> p = new Tupple<>(l,in_channel,out_channel);
		listeners.remove(p);
	}

	/**
	 * Gets the listener from this Component at the specified index.
	 * @param index The index of the listener.
	 * @return A listener in this component.
	 */
	Tupple<ComponentListener, Integer, Integer> getListener(int index) {
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
	 * @param val The new boolean value
	 * @param in_channel A Integer specifying which input
	 */

	public void update(boolean val,int in_channel){
		inputChannels[in_channel] = val;          // update the specified input
		boolean[] current = logic(inputChannels);    // Evaluate new output
		for (Tupple p :listeners) {                   // Broadcast new output to listeners (Components connected to output)
			((ComponentListener)(p.first())).update(current[(int) p.third()],(int) p.second());
		}
	}
}
