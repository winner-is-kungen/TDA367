package com.winner_is_kungen.tda367.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;

import java.io.IOException;

public class MenuBarController extends MenuBar {

	public MenuBarController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MenuBar.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

}
