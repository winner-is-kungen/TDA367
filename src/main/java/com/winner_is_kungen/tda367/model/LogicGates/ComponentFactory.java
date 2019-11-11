package com.winner_is_kungen.tda367.model.LogicGates;

import com.winner_is_kungen.tda367.model.Blueprint;
import com.winner_is_kungen.tda367.model.Component;

import java.util.Map;
import java.util.UUID;

public class ComponentFactory {
	private static IComponentFactoryMethod NOT = () -> new NotGate(UUID.randomUUID().toString());

	private static IComponentFactoryMethod AND = () -> new AndGate(UUID.randomUUID().toString(), 2);

	private static IComponentFactoryMethod OR = () -> new OrGate(UUID.randomUUID().toString(), 2);

	private static IComponentFactoryMethod INPUT = () -> new Input(UUID.randomUUID().toString());

	private static IComponentFactoryMethod OUTPUT = () -> new Output(UUID.randomUUID().toString());

	private static Map<String , IComponentFactoryMethod> componentMethods = Map.ofEntries(
		Map.entry(NotGate.getTypeID(), NOT),
		Map.entry(AndGate.getTypeID(), AND),
		Map.entry(OrGate.getTypeID(), OR),
		Map.entry(Input.getTypeID(), INPUT),
		Map.entry(Output.getTypeID(), OUTPUT)
	);

	public static Component createComponent(String id) {
		if (componentMethods.containsKey(id))
			return componentMethods.get(id).create();
		else
			return null;
	}

	/**
	 * Creates custom components based on Blueprints.
	 */
	public static Component createCustomComponent(String id, String name, Blueprint blueprint) {
		return new CustomComponent(id, name, blueprint);
	}

	public static Map<String, IComponentFactoryMethod> getComponents() {
		return componentMethods;
	}
}
