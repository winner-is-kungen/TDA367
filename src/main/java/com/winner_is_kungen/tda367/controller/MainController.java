package com.winner_is_kungen.tda367.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class MainController extends AnchorPane {

	@FXML
	private WorkspaceViewController workspaceviewController;

	public MainController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@FXML
	private void onItemClick(ComponentListController.ComponentListItemEvent event) {
		workspaceviewController.addNewComponent(event.getTypeID());
	}

}