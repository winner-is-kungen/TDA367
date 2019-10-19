package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Blueprint;
import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.LogicGates.ComponentFactory;
import com.winner_is_kungen.tda367.model.Workspace;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WorkspaceViewController extends TabPane {

	@FXML
	private BlueprintController blueprintController;

	private Workspace workspace;

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

		this.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {

				String fileName = newTab.getText();
				System.out.println(fileName);

				blueprintController.setBlueprint(workspace.getBlueprint(fileName));

				newTab.setContent(blueprintController);
			}
		});

	}

	public void addNewComponent(String typeID) {
		Component newComp = ComponentFactory.createComponent(typeID);
		blueprintController.addComponent(newComp);
	}

	public void createNewFile(String fileName) {

		Tab tab = new Tab();
		tab.setId(fileName);
		tab.setText(fileName);

		Blueprint newBlueprint = new Blueprint();
		workspace.addBlueprint(fileName, newBlueprint);

		Tab oldTab = this.getSelectionModel().getSelectedItem();
		System.out.println("selected tab " + oldTab.getText());
		tab.setContent(blueprintController);

		getTabs().add(tab);

	}

}
