package com.winner_is_kungen.tda367.model;

public class Input {
	private final Position position = new Position();
	private final String id;
	private boolean state = false;
	private static final String typeID = "INPUT";
	private ComponentListener connectedGate;
	private int connectedChannel;

	/**
	 * Constructor for input-component.
	 *
	 * @param id unique String specific to each instance of Input.
	 */
	public Input(String id) {
		this.id = id;
	}

	//Getters

	public Position getPosition() {
		return position;
	}

	public String getId() {
		return id;
	}

	public boolean getState() {
		return state;
	}

	public static String getTypeID() {
		return typeID;
	}

	/**
	 * Connects Input to a LogicGate. Upon connection it uppdates the
	 *
	 * @param gate      The gate to connect to.
	 * @param channelId Which channel on the specified gate to connect to.
	 */
	public void connectGate(ComponentListener gate, int channelId) {
		connectedGate = gate;
		connectedChannel = channelId;
		update();
	}

	/**
	 * Switches the current state of the Input and updates the connected gate, if one is connected.
	 */
	public void switchState() {
		state = !state;
		update();
	}

	/**
	 * Updates the connected gate with the current value of state.
	 */
	private void update() {
		if (connectedGate != null)
			connectedGate.update(state, connectedChannel);
	}
}
