package com.winner_is_kungen.tda367.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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


	// Test getting a list over all files names in the workspace

	@Test
	public void gettingListOfFilenames(){
		Set<String> filenames = new HashSet<>();
		filenames.add("key1");
		filenames.add("key2");
		filenames.add("key3");
		filenames.add("key4");

		assertEquals(filenames,workspace.getAllFilesNames());
	}

	// Test getting a Map over all blueprint and its names in the workspace

	@Test
	public void gettingListOffBlueprints(){

		Map<String,Blueprint> bluepintList = new HashMap<>();
		bluepintList.put("key1", blueprint1);
		bluepintList.put("key2", blueprint2);
		bluepintList.put("key3", blueprint3);
		bluepintList.put("key4", blueprint4);

		assertEquals(bluepintList,workspace.getBlueprintsList());
	}
}