package com.winner_is_kungen.tda367.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class WorkspaceTest {

	private Workspace workspace;
	private Blueprint blueprint1 = new Blueprint();
	private Blueprint blueprint2 = new Blueprint();
	private Blueprint blueprint3 = new Blueprint();
	private Blueprint blueprint4 = new Blueprint();
	private Blueprint blueprint5 = new Blueprint();

	@Before
	public void setup() {


		Map<String, Blueprint> blueprintMap = new HashMap<>();

		blueprintMap.put("key1", blueprint1);
		blueprintMap.put("key2", blueprint2);
		blueprintMap.put("key3", blueprint3);
		blueprintMap.put("key4", blueprint4);

		workspace = new Workspace(blueprintMap);

	}

	// Test getting a specific Blueprint by passing the file name

	@Test
	public void gettingSpecificBlueprint() {

		assertEquals(blueprint1, workspace.getBlueprint("key1"));

	}

	// Test adding Blueprint to workspace

	@Test
	public void addingBlueprintToWorkspace() {

		workspace.addBlueprint("key5", blueprint5);

		assertEquals(workspace.getBlueprint("key5"), blueprint5);
	}
}