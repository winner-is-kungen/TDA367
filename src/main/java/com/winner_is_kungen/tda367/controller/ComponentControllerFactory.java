package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Component;
import java.util.Map;

public class ComponentControllerFactory {
	/**
	 * A map containing type ids and their corresponding symbol.
	 */
	private static final Map<String, String> gateIcons = Map.ofEntries(
			//	Map.entry("test", "/gateIcons/test.png")
	);

	/**
	 * The default icon for unknown gates.
	 */
	private static final String defaultGateIcon = "?";

	/**
	 * Creates a controller for the given model. Chooses controller based on the type of the model.
	 *
	 * @param model The model that the controller should display.
	 * @return A controller that displays the model.
	 */
	public static ComponentController Create(Component model) {

		//  String symbol = "?";
		return new ComponentController(model, defaultGateIcon);
	}
}