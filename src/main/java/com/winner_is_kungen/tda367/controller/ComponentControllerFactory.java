package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.controller.LogicGates.OutputController;
import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.LogicGates.AndGate;
import com.winner_is_kungen.tda367.model.LogicGates.Input;
import com.winner_is_kungen.tda367.model.LogicGates.NotGate;
import com.winner_is_kungen.tda367.model.LogicGates.OrGate;
import com.winner_is_kungen.tda367.model.LogicGates.Output;

import java.util.Map;

public class ComponentControllerFactory {

	private static IComponentControllerFactoryMethod OUTPUT = model -> model instanceof Output ? new OutputController((Output)model) : null;

	/**
	 * A map containing type ids and their corresponding creator methods.
	 */
	private static final Map<String, IComponentControllerFactoryMethod> componentCreators = Map.ofEntries(
			Map.entry(Output.typeID, OUTPUT)
	);

	//#region Default Creator

	/**
	 * A map containing type ids and their corresponding Symbols.
	 */
	private static final Map<String, String> componentContent = Map.ofEntries(
		Map.entry(NotGate.typeID, "!" ),
		Map.entry(OrGate.typeID, "≥1" ),
		Map.entry(AndGate.typeID, "&" ),
		Map.entry(Input.getTypeID(), "i"),
		Map.entry(Output.typeID, "█")
	);

	/**
	 * A map containing type ids and their corresponding names.
	 */
	private static final Map<String, String> componentNames = Map.ofEntries(
		Map.entry(NotGate.typeID, "NOT-Gate" ),
		Map.entry(OrGate.typeID, "OR-Gate" ),
		Map.entry(AndGate.typeID, "AND-Gate" ),
		Map.entry(Input.getTypeID(), "Input"),
		Map.entry(Output.typeID, "OUTPUT")
	);

	/**
	 * The default icon for unknown gates.
	 */
	private static final String defaultGateSymbol = "?";

	/**
	 * Creates a `ComponentController` with the content of the supplied model.
	 *
	 * @param model The model that the controller should display.
	 * @return A controller that displays the model.
	 */
	private static ComponentController CreateDefault(Component model) {
		String symbol = componentContent.getOrDefault(model.getTypeId(), defaultGateSymbol);
		return new ComponentController(model, symbol);
	}

	//#endregion Default Creator

	/**
	 * Creates a controller for the given model. Chooses controller based on the type of the model.
	 *
	 * @param model The model that the controller should display.
	 * @return A controller that displays the model.
	 */
	public static ComponentController Create(Component model) {
		return componentCreators.getOrDefault(
			model.getTypeId(),
			ComponentControllerFactory::CreateDefault
		).Create(model);
	}

	/**
	 * Returns a name of a specific component based on the typeID you send in.
	 *
	 * @param typeID ID of the component you request a name for.
	 * @return A name for the specific component.
	 */
	public static String getComponentName(String typeID) {
		return componentNames.get(typeID);
	}

	public static String getComponentContent(String typeID) {
		return componentContent.get(typeID);
	}
}