package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Component;
import javafx.scene.image.Image;

import java.util.Map;

public class ComponentControllerFactory {
	/** A map containing type ids and their corresponding icons. */
	private static final Map<String, String> gateIcons = Map.ofEntries(
			Map.entry("NOT", "/gateIcons/test.png"),
			Map.entry("OR", "/gateIcons/test.png"),
			Map.entry("AND", "/gateIcons/test.png")
	);

	// Returns the path to a component-icon if given an existing component typeID.
	public static String getComponentIcon(String typeID){
		return gateIcons.getOrDefault(typeID, null);
	}

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
}