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

	private String path = null;

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
					workspaceviewController.createNewFile(result.get());
				}

				break;

			case "saveFile":
				if (workspaceviewController.getCurrentBlueprint().getPath() == null) {
					FileChooser fileChooser = new FileChooser();
					File selectedFile = fileChooser.showSaveDialog(menuBarController.getScene().getWindow());

					WriteFile writeFile = WriteFile.getWriteFileInstance();
					writeFile.write(workspaceviewController.getCurrentBlueprint(), selectedFile.getPath().replace(".dfbp", "") + ".dfbp");

					String name = selectedFile.getName();
					Tab tab = workspaceviewController.getTabs().get(workspaceviewController.getSelectionModel().getSelectedIndex());
					tab.setText(name);
					workspaceviewController.getCurrentBlueprint().setName(name);
				} else {
					WriteFile writeFile = WriteFile.getWriteFileInstance();
					writeFile.write(workspaceviewController.getCurrentBlueprint(), workspaceviewController.getCurrentBlueprint().getPath());
				}
				break;

			case "openWorkspace":
				DirectoryChooser directoryChooser = new DirectoryChooser();
				File selectedDirectory = directoryChooser.showDialog(menuBarController.getScene().getWindow());

				if (selectedDirectory != null) {
					this.path = selectedDirectory.getPath();
					ReadFile readFile = ReadFile.getReadFileInstance();
					ArrayList<File> files = new ArrayList<File>();

					File workspace = new File(selectedDirectory.getPath());

					File[] fileList = workspace.listFiles();

					for (File f : fileList) {
						if (f.getPath().contains(".dfbp")) {
							Tab newTab = new Tab();
							newTab.setId(readFile.read(f.getPath()).getName());
							newTab.setText(readFile.read(f.getPath()).getName());

							Blueprint newBp = readFile.read(f.getPath());

							workspaceviewController.addBlueprintToWorkspace(newBp);

							workspaceviewController.getTabs().add(newTab);
						}
					}
				}

				break;

			case "exit":
				Platform.exit();
				break;
		}
	}

}