package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.util.Tuple;

import java.util.UUID;

/**
 * Abstract class for Logic Components to extend from.
 */
public abstract class Component implements ComponentListener {
	private final Position position = new Position();
	private final String componentTypeID;
	private final String id;

	private final int nrInputs;
	private final int nrOutputs;

	private boolean[] inputChannels;
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
	public void addListener(ComponentListener listener, int inChannel, int outChannel) {
		listener.update(UUID.randomUUID().toString(),logic(inputChannels)[outChannel],inChannel);
		signal.add(new Tuple<>(listener, inChannel, outChannel));
	}

	/**
	 * Disconnects A Component from self
	 *
	 * @param listener          A object implementing ComponentListener
	 * @param in_channel A Integer specifying which input this is connected to
	 */
	void removeListener(ComponentListener listener, int in_channel, int out_channel) {
		Tuple<ComponentListener, Integer, Integer> connection = new Tuple<>(listener, in_channel, out_channel);
		signal.remove(connection);
	}

	/**
	 * Gets the listener from this Component at the specified index.
	 *
	 * @param index The index of the listener.
	 * @return A listener in this component.
	 */

	public Tuple<ComponentListener, Integer, Integer> getListener(int index) {
		return signal.get(index);

	}

	/**
	 * Gets the amount of listeners this Component updates.
	 *
	 * @return The amount of listeners this Component updates.
	 */
	public int getListenerSize() {
		return signal.size();
	}

	/**
	 * Takes the input values and returns the resulting result(s)
	 * Implemented by child classes
	 * @param vars  Array of booleans representing the input
	 * @return The array of results
	 */
	protected abstract boolean[] logic(boolean... vars);


	/**
	 * Updates the value of the input specified by channel to val.
	 * Also updates the output and sends signals to connected components to update their values
	 * If an input has already received an update it will ignore new updates until clearInputFlags() is called
	 *
	 * @param updateID  The current updates ID, used to check for self calling components
	 * @param val       The new boolean value
	 * @param inChannel A Integer specifying which input channel the new value is sent to
	 */

	public void update(String updateID, boolean val, int inChannel) {
		if (updateID.equals(lastUpdateIDs[inChannel]) || inputChannels[inChannel] == val) return;

		lastUpdateIDs[inChannel] = updateID;
		inputChannels[inChannel] = val;          // update the specified input
		boolean[] current = logic(inputChannels);    // Evaluate new output

		updateListeners(updateID, current);
	}

	protected void updateListeners(String updateID, boolean... current){
		signal.broadcastUpdate(updateID, current);
	}
}
