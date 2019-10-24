package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Blueprint;
import com.winner_is_kungen.tda367.services.ReadFile;
import com.winner_is_kungen.tda367.services.WriteFile;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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


	/**
	 * An event handler to handle ON_ITEM_CLICK for menu bar items, it gets the event from MenuBarController and handle
	 * it here depending on which item was clicked.
	 *
	 * @param event
	 */

	@FXML
	private void menuBarItemsHandler(MenuBarController.MenuItemEvent event) {

		switch (event.getMenuItem()) {
			case "newFile":

				TextInputDialog chooseName = new TextInputDialog();
				chooseName.setTitle("Please enter the name of the file");
				chooseName.setHeaderText("New File...");
				chooseName.setContentText("Please enter file name:");

				Optional<String> result = chooseName.showAndWait();
				if (result.isPresent() && result.get().strip().length() >= 1) {
					workspaceviewController.createNewFile(result.get().replace(".dfbp", "") + ".dfbp");
				}

				break;

			case "saveFile":
				workspaceviewController.saveFile();
				break;

			case "openWorkspace":
				DirectoryChooser directoryChooser = new DirectoryChooser();
				File selectedDirectory = directoryChooser.showDialog(menuBarController.getScene().getWindow());

				if (selectedDirectory != null) {
					workspaceviewController.loadWorkspace(selectedDirectory.getPath());
				}

				break;

			case "exit":
				Platform.exit();
				break;
		}
	}

}