package com.winner_is_kungen.tda367.model.LogicGates;

import com.winner_is_kungen.tda367.model.Component;

public class NotGate extends Component {
	private final static String typeID = "NOT";

	public NotGate(String id) {
		super(id, getTypeID(), 1, 1);
	}

	public static String getTypeID() {
		return typeID;
	}

	protected boolean[] logic(boolean... vars) {
		boolean[] b = new boolean[1];
		b[0] = !vars[0];
		return b;
	}
}
