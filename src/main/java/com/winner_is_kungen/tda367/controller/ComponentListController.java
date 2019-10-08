package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.LogicGates.ComponentFactory;
import com.winner_is_kungen.tda367.model.LogicGates.IComponentFactoryMethod;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.Map;

public class ComponentListController extends ScrollPane {

	@FXML private FlowPane componentListContent;

	public ComponentListController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ComponentList.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		}
		catch (IOException ex) { throw new RuntimeException(ex);
		}

		componentListContent.getChildren().clear();
		Map<String , IComponentFactoryMethod> components = ComponentFactory.getComponents();
		for (String key : components.keySet()){
			//String content = ComponentControllerFactory.getComponentIcon(id)
			//String name = ComponentControllerFactory.getComponentName(id)
			ComponentListItemController item = new ComponentListItemController(key, key);
			componentListContent.getChildren().add(item);
		}
	}

}
