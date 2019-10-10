package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Component;
import javafx.scene.image.Image;

import java.util.Map;

public class ComponentControllerFactory {
	/**
	 * A map containing type ids and their corresponding icons.
	 */
	private static final Map<String, String> componentContent = Map.ofEntries(
		Map.entry("NOT", "!" ),
		Map.entry("AND", "&" ),
		Map.entry("OR", "≥1" )
	);

	/**
	 * A map containing type ids and their corresponding names.
	 */
	private static final Map<String, String> componentNames = Map.ofEntries(
		Map.entry("NOT", "NOT-Gate" ),
		Map.entry("AND", "AND-Gate" ),
		Map.entry("OR", "OR-Gate" )
	);

	/**
	 * The default icon for unknown gates.
	 */
	private static final String defaultGateIcon = "/gateIcons/unknown.png";

	/**
	 * Creates a controller for the given model. Chooses controller based on the type of the model.
	 *
	 * @param model The model that the controller should display.
	 * @return A controller that displays the model.
	 */
	public static ComponentController Create(Component model) {
		Image image = new Image(ComponentControllerFactory.class.getResourceAsStream(componentContent.getOrDefault("test" /*model.type*/, defaultGateIcon)));
		return new ComponentController(model, image);
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