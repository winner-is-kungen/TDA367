package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.LogicGates.AndGate;
import com.winner_is_kungen.tda367.model.LogicGates.NotGate;
import com.winner_is_kungen.tda367.model.LogicGates.OrGate;
import javafx.scene.image.Image;

import java.util.Map;

public class ComponentControllerFactory {
	/** A map containing type ids and their corresponding icons. */
	private static final Map<String, String> gateIcons = Map.ofEntries(
			Map.entry(NotGate.typeID, "/gateIcons/test.png"),
			Map.entry(OrGate.typeID, "/gateIcons/test.png"),
			Map.entry(AndGate.typeID, "/gateIcons/test.png")
	);

	/** The default icon for unknown gates. */
	private static final String defaultGateIcon = "/gateIcons/unknown.png";

	/**
	 * Creates a controller for the given model. Chooses controller based on the type of the model.
	 * @param model The model that the controller should display.
	 * @return A controller that displays the model.
	 */
	public static ComponentController Create(Component model) {
		Image image = new Image(ComponentControllerFactory.class.getResourceAsStream(gateIcons.getOrDefault("test" /*model.type*/, defaultGateIcon)));
		return new ComponentController(model, image);
	}

	// Returns the path to a component-icon if given an existing component typeID.
	public static String getComponentIcon(String typeID){
		return gateIcons.getOrDefault(typeID, null);
	}
}