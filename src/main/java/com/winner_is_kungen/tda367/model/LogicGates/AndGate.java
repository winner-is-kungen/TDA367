package com.winner_is_kungen.tda367.model.LogicGates;

import com.winner_is_kungen.tda367.model.Component;

public class AndGate extends Component {
	private final static String typeID = "AND";

	/**
	 * Constructor for the Component
	 *
	 * @param id     an Integer specifying the given id for the component
	 * @param inputs an Integer specifying the number of inputs the component has
	 */
	public AndGate(String id, int inputs) {
		super(id, getTypeID(), inputs, 1);
	}

	public static String getTypeID() {
		return typeID;
	}

	@Override
	protected boolean[] logic(boolean... vars) {
		boolean[] tmp = new boolean[1];
		tmp[0] = true;
		for(boolean b : vars){
			tmp[0] = tmp[0] && b;
		}
		return tmp;
	}
}
