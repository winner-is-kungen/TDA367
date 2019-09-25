package com.winner_is_kungen.tda367.model.LogicGates;
import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.SimulationManager;

public class AndGate extends Component {
	public AndGate(int id){
		super(id,2);
	}
	public AndGate(int id, SimulationManager simulationManager){super(id,2,simulationManager);}


	protected boolean[] logic(boolean... vars){
		boolean[] b = new boolean[1];
		b[0] = vars[0] && vars[1];
		return b;
	}
}
