package com.winner_is_kungen.tda367.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class WorkspaceViewController extends TabPane {

	protected void createNewFile() {

	}

	public WorkspaceViewController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/WorkspaceView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

}
