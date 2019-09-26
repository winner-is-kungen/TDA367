package com.winner_is_kungen.tda367.model.LogicGates;
import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.SimulationManager;

public class NotGate extends Component {
	public NotGate(int id){
		super(id, 1,1);
	}
	public NotGate(int id, SimulationManager simulationManager){super(id,1,simulationManager);}

	protected boolean[] logic(boolean... vars){
		boolean[] b = new boolean[1];
		b[0] = !vars[0];
		return b;
	}
}
