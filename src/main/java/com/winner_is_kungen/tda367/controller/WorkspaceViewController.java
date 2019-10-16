package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Blueprint;
import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.LogicGates.ComponentFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class WorkspaceViewController extends TabPane {

	@FXML
	private TabPane workspaceView;

	@FXML
	private BlueprintController blueprintController;

	public WorkspaceViewController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/WorkspaceView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		this.blueprintController.setBlueprint(new Blueprint());
	}

	public void addNewComponent(String typeID) {
		Component newComp = ComponentFactory.createComponent(typeID);
		blueprintController.addComponent(newComp);
	}

	public void createNewFile() {

		Tab tab = new Tab();
		tab.setText("Untitled Tab");
		tab.setContent(blueprintController);
		workspaceView.getTabs().add(tab);

	}

}
