package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.util.Tuple;

import java.util.Arrays;
import java.util.UUID;

/**
 * Abstract class for Logic Components to extend from.
 */
public abstract class Component implements ComponentListener {
	private final Position position = new Position();
	private final int nrInputs;               // Specifies number of inputs the component has
	private boolean[] inputChannels;    // Stores input values from previous simulations
	private final int nrOutputs;              // Specifies number of outputs the component has
	private final String id;                     // Identification of node, placeholder
	private final String componentTypeID;

	private String[] lastUpdateIDs;

	private Signal signal = new Signal();

	/**
	 * Constructor for the Component
	 *
	 * @param id     an Integer specifying the given id for the component
	 * @param inputs an Integer specifying the number of inputs the component has
	 */
	public Component(String id, String componentTypeID, int inputs, int outputs) {
		this.nrInputs = inputs;
		this.id = id;
		this.nrOutputs = outputs;
		this.inputChannels = new boolean[nrInputs];
		this.lastUpdateIDs = new String[nrInputs];
		this.componentTypeID = componentTypeID;

	}

	/**
	 * Getter for the components id
	 *
	 * @return the id for the component in the form of a String
	 */
	public String getId() {
		return id;
	}

	public String getTypeId() {
		return componentTypeID;
	}

	/**
	 * Gets the Position of this Component.
	 *
	 * @return The Position of this Component.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Gets the number of incoming channels this component has.
	 *
	 * @return The number of incoming channels this component has.
	 */
	public int getNrInputs() {
		return nrInputs;
	}

	/**
	 * Gets the number of outgoing channels this component has.
	 *
	 * @return The number of outgoing channels this component has.
	 */
	public int getNrOutputs() {
		return nrOutputs;
	}

	/**
	 * Connects the given input of a component to self output.
	 *
	 * @param listener   A object implementing ComponentListener.
	 * @param inChannel  A Integer specifying which input is used.
	 * @param outChannel A Integer specifying which input is used.
	 */
	void addListener(ComponentListener listener, int inChannel, int outChannel) {
		signal.add(new Tuple<>(listener, inChannel, outChannel));

		signal.broadcastUpdate(UUID.randomUUID().toString(),logic(inputChannels));
	}

	/**
	 * Disconnects A Component from self
	 *
	 * @param l          A object implementing ComponentListener
	 * @param in_channel A Integer specifying which input this is connected to
	 */
	void removeListener(ComponentListener l, int in_channel, int out_channel) {
		Tuple<ComponentListener, Integer, Integer> p = new Tuple<>(l, in_channel, out_channel);
		signal.remove(p);
	}

	/**
	 * Gets the listener from this Component at the specified index.
	 *
	 * @param index The index of the listener.
	 * @return A listener in this component.
	 */
	Tuple<ComponentListener, Integer, Integer> getListener(int index) {
		return signal.get(index);
	}

	/**
	 * Gets the amount of listeners this Component updates.
	 *
	 * @return The amount of listeners this Component updates.
	 */
	int getListenerSize() {
		return signal.size();
	}

	protected abstract boolean[] logic(boolean... vars); // Takes an array of booleans and returns a boolean
	// Is to be implemented by the extending class

	/**
	 * Updates the value of the input specified by channel to val.
	 * Also updates the output and sends signals to connected components to update their values
	 * If an input has already received an update it will ignore new updates until clearInputFlags() is called
	 *
	 * @param val       The new boolean value
	 * @param inChannel A Integer specifying which input
	 */

	public void update(String updateID, boolean val, int inChannel) {
		if(updateID.equals(lastUpdateIDs[inChannel])) return;

		lastUpdateIDs[inChannel] = updateID;
		inputChannels[inChannel] = val;

		signal.broadcastUpdate(updateID,logic(inputChannels));
	}
}
