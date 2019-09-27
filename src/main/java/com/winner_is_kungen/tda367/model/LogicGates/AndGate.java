package com.winner_is_kungen.tda367.model.LogicGates;
import com.winner_is_kungen.tda367.model.Component;

public class AndGate extends Component {
	public AndGate(int id){
		super(id,2,1);
	}

	protected boolean[] logic(boolean... vars){
		boolean[] b = new boolean[1];
		b[0] = vars[0] && vars[1];
		return b;
	}
}
