package com.winner_is_kungen.tda367.model.LogicGates;

import com.winner_is_kungen.tda367.model.Component;

import java.util.Map;
import java.util.UUID;

public class ComponentFactory {

	private static IComponentFactoryMethod NOT = () -> new NotGate(UUID.randomUUID().toString());

	private static IComponentFactoryMethod AND = () -> new AndGate(UUID.randomUUID().toString(), 2);

	private static IComponentFactoryMethod OR = () -> new OrGate(UUID.randomUUID().toString(), 2);

	private static IComponentFactoryMethod INPUT = () -> new InputComponent(UUID.randomUUID().toString());

	private static Map<String , IComponentFactoryMethod> componentMethods = Map.ofEntries(
		Map.entry(NotGate.typeID, NOT),
		Map.entry(AndGate.typeID, AND),
		Map.entry(OrGate.typeID, OR),
		Map.entry(InputComponent.getTypeID(), INPUT)
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
