package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.util.ConnectionRecord;

import java.util.ArrayList;
import java.util.List;
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

	public boolean getCurrentOutput(int channel){
		boolean[] outputValue = signal.getNewValues();
		return outputValue[channel];
	}


	/**
	 * Connects the given input of a component to self output.
	 *
	 * @param listener   A object implementing ComponentListener.
	 * @param inChannel  A Integer specifying which input is used.
	 * @param outChannel A Integer specifying which input is used.
	 */
	public void addListener(ComponentListener listener, int inChannel, int outChannel) {
		listener.update(logic(inputChannels)[outChannel], inChannel);
		signal.add(new ConnectionRecord(listener, inChannel, outChannel));
	}

	/**
	 * Disconnects A Component from self
	 *
	 * @param listener          A object implementing ComponentListener
	 * @param in_channel A Integer specifying which input this is connected to
	 */
	void removeListener(ComponentListener listener, int in_channel, int out_channel) {
		ConnectionRecord connection = new ConnectionRecord(listener, in_channel, out_channel);
		signal.remove(connection);
	}

	/**
	 * Gets the listener from this Component at the specified index.
	 *
	 * @param index The index of the listener.
	 * @return A listener in this component.
	 */

	public ConnectionRecord getListener(int index) {
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
	 * @param updateRecords A list of all previously encountered component and channel combinations.
	 * @param val           The new boolean value
	 * @param inChannel     A Integer specifying which input channel the new value is sent to
	 */

	public void update(List<ComponentUpdateRecord> updateRecords, boolean val, int inChannel) {
		ComponentUpdateRecord record = new ComponentUpdateRecord(id, inChannel);

		if (updateRecords.contains(record)) return;

		inputChannels[inChannel] = val;          // update the specified input
		boolean[] current = logic(inputChannels);    // Evaluate new output

		List<ComponentUpdateRecord> newUpdateRecords = new ArrayList<ComponentUpdateRecord>(updateRecords);
		newUpdateRecords.add(record);
		updateListeners(newUpdateRecords, current);
	}

	protected void updateListeners(List<ComponentUpdateRecord> updateRecords, boolean... current){
		signal.broadcastUpdate(updateRecords, current);
	}
}
