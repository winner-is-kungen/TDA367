package com.winner_is_kungen.tda367.model.LogicGates;

import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.ComponentListener;
import com.winner_is_kungen.tda367.model.util.Tuple;

public class Input extends Component {
	private static final String typeID = "INPUT";
	private boolean state = false;

	/**
	 * Constructor for input-component.
	 *
	 * @param id unique String specific to each instance of Input.
	 */
	public Input(String id) {
		super(id, typeID, 0, 1);
	}

	//Getters

	public boolean getState() {
		return state;
	}

	public static String getTypeID() {
		return typeID;
	}

	public void switchState() {
		state = !state;
		updateListeners(state);
	}

	protected boolean[] logic(boolean... vars) {
		boolean[] b = new boolean[1];
		b[0] = state;
		return b;
	}

}
