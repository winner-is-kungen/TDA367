package com.winner_is_kungen.tda367.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class ComponentListItemController extends AnchorPane {

	private String ID;

	@FXML
	private Text componentListItemContent;
	@FXML
	private Text componentListItemName;

	public ComponentListItemController(String content, String name) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ComponentListItem.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		componentListItemContent.setText(content);
		componentListItemName.setText(name);
		this.ID = name;
	}

}
