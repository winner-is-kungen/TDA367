package com.winner_is_kungen.tda367.model.LogicGates;

import com.winner_is_kungen.tda367.model.Component;

public class OrGate extends Component {
	public final static String typeID = "OR";

	/**
	 * Constructor for the Component
	 *
	 * @param id     an Integer specifying the given id for the component
	 * @param inputs an Integer specifying the number of inputs the component has
	 */
	public OrGate(int id, int inputs) {
		super(id, typeID, inputs, 1);
	}

	@Override
	protected boolean[] logic(boolean... vars) {
		boolean[] tmp = new boolean[1];
		for(boolean b : vars){
			tmp[0] = b || tmp[0];
		}
		return tmp;
	}
}
