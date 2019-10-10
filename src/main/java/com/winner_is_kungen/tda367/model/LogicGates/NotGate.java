package com.winner_is_kungen.tda367.model.LogicGates;

import com.winner_is_kungen.tda367.model.Component;

public class NotGate extends Component {
	public final static String typeID = "NOT";

	public NotGate(int id){
		super(id, typeID,1,1);
	}

	protected boolean[] logic(boolean... vars) {
		boolean[] b = new boolean[1];
		b[0] = !vars[0];
		return b;
	}
}
