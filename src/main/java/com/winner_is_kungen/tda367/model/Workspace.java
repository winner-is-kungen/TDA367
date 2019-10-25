package com.winner_is_kungen.tda367.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Workspace {

	/**
	 * A Map of Blueprint list that has a name of the file as a key and Blueprint as a value
	 */
	private Map<String, Blueprint> blueprintsList = new HashMap<>();

	/**
	 * Workspace constructor
	 *
	 * @param blueprintsList a Map list with name as a key and Blueprint as a value
	 */
	public Workspace(Map<String, Blueprint> blueprintsList) {
		this.blueprintsList = blueprintsList;
	}

	/**
	 * Method that returns Blueprint by passing it a name as a key
	 *
	 * @param fileName name of the Blueprint file
	 * @return the blueprint that has the key fileName
	 */
	public Blueprint getBlueprint(String fileName) {
		return blueprintsList.get(fileName);
	}

	/**
	 * A method to add Blueprint to the Map blueprintsList
	 *
	 * @param fileName  name of the file as a key
	 * @param blueprint blueprint object as the value
	 */
	public void addBlueprint(String fileName, Blueprint blueprint) {
		blueprintsList.put(fileName, blueprint);
	}

	public void resetWorkspace() {
		blueprintsList.clear();
	}
}