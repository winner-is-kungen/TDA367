package com.winner_is_kungen.tda367.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Optional;

public class MainController extends AnchorPane {

	@FXML
	private WorkspaceViewController workspaceviewController;

	@FXML
	private MenuBarController menuBarController;

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


	@FXML
	private void menuBarItemsHandler(MenuBarController.MenuItemEvent event) {

		if (event.getMenuItem() == "newFile") {

			TextInputDialog chooseName = new TextInputDialog();
			chooseName.setTitle("Please enter the name of the file");
			chooseName.setHeaderText("New File...");
			chooseName.setContentText("Please enter file name:");

			Optional<String> result = chooseName.showAndWait();
			if (result.isPresent() && result.get().strip().length() >= 1) {
				workspaceviewController.createNewFile(result.get());
			}


		} else if (event.getMenuItem() == "saveFile") {
			//TODO save function

		} else if (event.getMenuItem() == "exit") {
			Platform.exit();
		}


	}
}