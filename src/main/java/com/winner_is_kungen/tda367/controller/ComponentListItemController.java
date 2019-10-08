package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.LogicGates.IComponentFactoryMethod;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Map;

public class ComponentListItemController extends AnchorPane {

	private String ID;

	@FXML private Text componentListItemContent;
	@FXML private Text componentListItemName;
	@FXML private AnchorPane componentListItemArea;

	public ComponentListItemController(String content, String name) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ComponentListItem.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		componentListItemContent.setText(content);
		componentListItemName.setText(name);
		this.ID = name;
	}

	@FXML
	private void createComponent(){
		System.out.println(ID);
		//TODO
	}

}
