package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Blueprint;
import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.LogicGates.ComponentFactory;
import com.winner_is_kungen.tda367.model.Workspace;
import com.winner_is_kungen.tda367.services.ReadFile;
import com.winner_is_kungen.tda367.services.WriteFile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WorkspaceViewController extends TabPane {

	BlueprintController bpController;

	private Workspace workspace = null;

	private String path = null;

	public WorkspaceViewController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/WorkspaceView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		this.bpController = new BlueprintController();
		clearAllTabs();

		/*
		  A Listener to check which tab is selected and set the right blueprint and blueprintController
		 */

		this.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {

				if (newTab != null) {
					String fileName = newTab.getText();

					bpController.setBlueprint(workspace.getBlueprint(fileName));

					if (oldTab != null) {
						oldTab.setContent(null);
					}
					newTab.setContent(bpController);
				}
			}
		});

	}

	public void addNewComponent(String typeID) {
		Component newComp = ComponentFactory.createComponent(typeID);
		bpController.addComponent(newComp);
	}

	public Blueprint getCurrentBlueprint() {
		return bpController.getCurrentBlueprint();
	}

	/**
	 * This method creates new file (Blueprint) and shows it as new tab, its called from the main controller by
	 * an event handler for the menuBarController.
	 *
	 * @param fileName the name of the new file gotten from user input in the main controller
	 */

	void createNewFile(String fileName) {
		Tab newTab = new Tab();
		newTab.setId(fileName);
		newTab.setText(fileName);

		Blueprint newBlueprint = new Blueprint();
		newBlueprint.setName(fileName);
		workspace.addBlueprint(fileName, newBlueprint);

		bpController.setBlueprint(workspace.getBlueprint(fileName));
		if (getSelectionModel().getSelectedItem() != null) {
			getSelectionModel().getSelectedItem().setContent(null);
		}
		newTab.setContent(bpController);

		getTabs().add(newTab);
		getSelectionModel().select(newTab);
	}

	public void addBlueprintToWorkspace(Blueprint bp) {
		workspace.addBlueprint(bp.getName(), bp);
	}

	public void clearAllTabs() {
		if (workspace != null) {
			workspace.resetWorkspace();
		}
		getTabs().clear();

	}

	public BlueprintController getBlueprintController() {
		return bpController;
	}

	public void loadWorkspace(String path) {
		if (workspace == null) {
			Map<String, Blueprint> newMap = new HashMap<>();
			workspace = new Workspace(newMap);
		}

		clearAllTabs();

		this.path = path;
		ReadFile readFile = ReadFile.getReadFileInstance();
		ArrayList<File> files = new ArrayList<File>();

		File directory = new File(path);

		File[] fileList = directory.listFiles();

		for (File f : fileList) {
			if (f.getPath().contains(".dfbp")) {
				Tab newTab = new Tab();
				newTab.setId(readFile.read(f.getPath()).getName());
				newTab.setText(readFile.read(f.getPath()).getName());

				Blueprint newBp = readFile.read(f.getPath());

				addBlueprintToWorkspace(newBp);

				getTabs().add(newTab);

				if (getSelectionModel().getSelectedItem() != null) {
					getSelectionModel().getSelectedItem().setContent(null);
				}
				getSelectionModel().select(newTab);
				newTab.setContent(getBlueprintController());
			}
		}
	}

	public void saveFile() {
		if (this.path != null) {
			WriteFile writeFile = WriteFile.getWriteFileInstance();
			writeFile.write(getCurrentBlueprint(), path + File.separator + getCurrentBlueprint().getName());
		}
	}

	public String getDirectory() {
		return path;
	}
}
