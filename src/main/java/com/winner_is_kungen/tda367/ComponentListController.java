package com.winner_is_kungen.tda367;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;

import java.io.IOException;

public class ComponentListController extends ScrollPane {

	public ComponentListController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ComponentList.fxml"));
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