package com.winner_is_kungen.tda367.model.LogicGates;
import com.winner_is_kungen.tda367.model.Component;

public class NotGate extends Component {
	public NotGate(int id){
		super(id,1);
	}

	protected boolean logic(boolean... vars){
		return !vars[0];
	}
}
