package com.winner_is_kungen.tda367.model.LogicGates;

import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.LogicGates.AndGate;
import com.winner_is_kungen.tda367.model.LogicGates.NotGate;
import com.winner_is_kungen.tda367.model.LogicGates.OrGate;

import java.util.Map;
import java.util.UUID;

public class ComponentFactory {

	private static IComponentFactoryMethod NOT = () -> new NotGate(UUID.randomUUID().toString());

	private static IComponentFactoryMethod AND = () -> new AndGate(UUID.randomUUID().toString(), 2);

	private static IComponentFactoryMethod OR = () -> new OrGate(UUID.randomUUID().toString(), 2);

	private static Map<String , IComponentFactoryMethod> componentMethods = Map.ofEntries(
		Map.entry(NotGate.typeID, NOT),
		Map.entry(AndGate.typeID, AND),
		Map.entry(OrGate.typeID, OR)
	);

	public static Component createComponent(String id){
		if (componentMethods.containsKey(id))
			return componentMethods.get(id).create();
		else
			return null;
	}

	public static Map<String, IComponentFactoryMethod> getComponents() {
		return componentMethods;
	}
}
