package com.winner_is_kungen.tda367.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class WorkspaceTest {

	private Workspace workspace;
	private Blueprint blueprint1 = new Blueprint();
	private Blueprint blueprint2 = new Blueprint();
	private Blueprint blueprint3 = new Blueprint();
	private Blueprint blueprint4 = new Blueprint();


	@Before
	public void setup() {


		Map<String, Blueprint> blueprintMap = new HashMap<>();

		blueprintMap.put("key1", blueprint1);
		blueprintMap.put("key2", blueprint2);
		blueprintMap.put("key3", blueprint3);

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

		workspace.addBlueprint("key4", blueprint4);

		assertEquals(workspace.getBlueprint("key4"), blueprint4);
	}
}