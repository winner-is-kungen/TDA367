package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.AndGate;
import com.winner_is_kungen.tda367.model.LogicGates.NotGate;
import com.winner_is_kungen.tda367.model.LogicGates.OrGate;

public class ComponentFactory {

	public static Component createNot(int id){
		return new NotGate(id);
	}

	public static Component createAnd(int id, int nrInputs){
		return new AndGate(id, nrInputs);
	}

	public static Component createOr(int id, int nrInputs){
		return new OrGate(id, nrInputs);
	}
}
