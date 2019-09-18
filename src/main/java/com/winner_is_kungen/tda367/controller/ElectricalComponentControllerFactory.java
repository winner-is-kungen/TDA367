package com.winner_is_kungen.tda367.controller;

import java.util.Map;

public class ElectricalComponentControllerFactory {
	/**
	 * A map containing type ids and factory methods for those types.
	 */
	private static final Map<String, IElectricalComponentControllerFactoryMethod> factoryMethods = Map.ofEntries(
//			Map.entry("test", Test::factoryMethod)
	);

	/**
	 * Creates a controller for the given model. Chooses controller based on the type of the model.
	 * @param model The model that the controller should display.
	 * @return A controller that displays the model.
	 */
	public static ElectricalComponentController Create(/*ElectricalComponent model*/) {
		if (factoryMethods.containsKey(null /*model.type*/)) {
			return factoryMethods.get(null /*model.type*/).Create(/*model*/);
		}
		else {
			return null;
		}
	}
}