package com.winner_is_kungen.tda367.model.LogicGates;

import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.LogicGates.AndGate;
import com.winner_is_kungen.tda367.model.LogicGates.NotGate;
import com.winner_is_kungen.tda367.model.LogicGates.OrGate;

import java.util.Map;

public class ComponentFactory {

	private static Map<String , Component> componentMethods = Map.ofEntries(
		Map.entry("NOT", new NotGate(-1)),
		Map.entry("AND", new AndGate(-1, 2)),
		Map.entry("OR", new OrGate(-1, 2))
	);

	public static Component createComponent(String id){
		if (componentMethods.containsKey(id))
			return componentMethods.get(id);
		else
			return null;
	}


}
