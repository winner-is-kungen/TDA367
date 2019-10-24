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

	@FXML
	private BlueprintController blueprintController;

	private Workspace workspace;

	private String path;

	public WorkspaceViewController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/WorkspaceView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		Map<String, Blueprint> blueprintMap = new HashMap<>();
		blueprintMap.put("new file", new Blueprint());
		workspace = new Workspace(blueprintMap);

		blueprintController.setBlueprint(workspace.getBlueprint("new file"));

		/*
		  A Listener to check which tab is selected and set the right blueprint and blueprintController
		 */

		this.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {

				if (newTab != null){
					String fileName = newTab.getText();

					blueprintController.setBlueprint(workspace.getBlueprint(fileName));

					if (oldTab != null) {
						oldTab.setContent(null);
					}
					newTab.setContent(blueprintController);
				}
			}
		});

	}

	public void addNewComponent(String typeID) {
		Component newComp = ComponentFactory.createComponent(typeID);
		blueprintController.addComponent(newComp);
	}

	public Blueprint getCurrentBlueprint() {
		return blueprintController.getCurrentBlueprint();
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
		newBlueprint.setPath(this.path + File.separator + fileName);
		workspace.addBlueprint(fileName, newBlueprint);

		Tab oldTab = this.getSelectionModel().getSelectedItem();
		blueprintController.setBlueprint(workspace.getBlueprint(fileName));
		oldTab.setContent(null);
		newTab.setContent(blueprintController);

		getTabs().add(newTab);
		getSelectionModel().select(newTab);
	}

	public void addBlueprintToWorkspace(Blueprint bp) {
		workspace.addBlueprint(bp.getName(), bp);
	}

	public void clearAllTabs(){
		Set<String> tabs = workspace.getAllFilesNames();
		for (String name : tabs){
			workspace.removeBlueprint(name);
		}

		this.getTabs().clear();
	}

	public void setCurrentBlueprint(Blueprint bp) {
		blueprintController.setBlueprint(bp);
	}

	public BlueprintController getBlueprintController(){
		return blueprintController;
	}

	public void loadWorkspace(String path){
		this.clearAllTabs();

		this.path = path;
		ReadFile readFile = ReadFile.getReadFileInstance();
		ArrayList<File> files = new ArrayList<File>();

		File workspace = new File(path);

		File[] fileList = workspace.listFiles();

		for (File f : fileList) {
			if (f.getPath().contains(".dfbp")) {
				Tab newTab = new Tab();
				newTab.setId(readFile.read(f.getPath()).getName());
				newTab.setText(readFile.read(f.getPath()).getName());

				Blueprint newBp = readFile.read(f.getPath());

				newBp.setPath(f.getPath());

				this.addBlueprintToWorkspace(newBp);

				this.getTabs().add(newTab);

				this.getSelectionModel().select(newTab);
				Tab oldTab = this.getSelectionModel().getSelectedItem();
				oldTab.setContent(null);
				newTab.setContent(this.getBlueprintController());
			}
		}
	}

	public void saveNonPathFile(){
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showSaveDialog(this.getScene().getWindow());

		WriteFile writeFile = WriteFile.getWriteFileInstance();
		String filePath = selectedFile.getPath().replace(".dfbp", "") + ".dfbp";
		writeFile.write(this.getCurrentBlueprint(), filePath);

		String name = selectedFile.getName();
		Tab tab = this.getTabs().get(this.getSelectionModel().getSelectedIndex());
		tab.setText(name);
		this.getCurrentBlueprint().setName(name);

		this.getCurrentBlueprint().setPath(filePath);
	}

	public void savePathFile(){
		WriteFile writeFile = WriteFile.getWriteFileInstance();
		writeFile.write(this.getCurrentBlueprint(), this.getCurrentBlueprint().getPath());
	}

}
