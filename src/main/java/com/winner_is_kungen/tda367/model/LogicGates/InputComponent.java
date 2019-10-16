package com.winner_is_kungen.tda367.model.LogicGates;

import com.winner_is_kungen.tda367.model.Component;

public class InputComponent extends Component {
	private static final String typeID = "INPUT";

	/**
	 * Constructor for input-component.
	 *
	 * @param id unique String specific to each instance of Input.
	 */
	public InputComponent(String id) {
		super(id, typeID, 1, 1);
	}

	//Getters

	public static String getTypeID() {
		return typeID;
	}

	protected boolean[] logic(boolean... vars) {
		boolean[] b = new boolean[1];
		b[0] = vars[0];
		return b;
	}
}
