package com.winner_is_kungen.tda367;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;

import java.io.IOException;

public class TabController extends TabPane{

	//public void openWorkspaceTabs() {
	//	Tab firstTab = new Tab("Testing");
	//	tabPane.getTabs().clear();
	//	tabPane.getTabs().add(firstTab);
	//}

	//public void createNewTab() {
	//	Tab tab = new Tab("Test");
	//	tabPane.getTabs().add(tab);
	//}

	public TabController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Workspace.fxml"));
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
