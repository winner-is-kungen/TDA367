package com.winner_is_kungen.tda367.model;

import java.util.HashMap;
import java.util.Map;

public class Workspace {

	private Map<String,Blueprint> blueprintsList = new HashMap<>();

	public Workspace(Map<String, Blueprint> blueprintsList) {
		this.blueprintsList = blueprintsList;
	}

	private Blueprint getBlueprint(String fileName){
		return blueprintsList.get(fileName);
	}

	private void addBlueprint(String fileName,Blueprint blueprint){
		blueprintsList.put(fileName,blueprint);
	}




}
