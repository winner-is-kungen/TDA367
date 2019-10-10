package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.LogicGates.ComponentFactory;
import com.winner_is_kungen.tda367.model.LogicGates.IComponentFactoryMethod;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.Map;

public class ComponentListController extends ScrollPane {

	@FXML
	private FlowPane componentListContent;

	private EventHandler<ComponentListItemEvent> onItemClickEventHandler;

	public ComponentListController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ComponentList.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		componentListContent.getChildren().clear();
		Map<String, IComponentFactoryMethod> components = ComponentFactory.getComponents();
		for (String key : components.keySet()) {
			String content = ComponentControllerFactory.getComponentContent(key);
			String name = ComponentControllerFactory.getComponentName(key);
			ComponentListItemController item = new ComponentListItemController(content, name);
			item.setOnMouseClicked(e -> {
				Event ev = new ComponentListItemEvent(ComponentListItemEvent.ON_ITEM_CLICK, key);
				fireEvent(ev);
			});
			componentListContent.getChildren().add(item);
		}

	}

	public void setOnItemClick(EventHandler<ComponentListItemEvent> value) {
		if (onItemClickEventHandler != null) {
			removeEventHandler(ComponentListItemEvent.ON_ITEM_CLICK, onItemClickEventHandler);
		}

		onItemClickEventHandler = value;
		addEventHandler(ComponentListItemEvent.ON_ITEM_CLICK, value);
	}

	public EventHandler<ComponentListItemEvent> getOnItemClick() {
		return onItemClickEventHandler;
	}

	public static class ComponentListItemEvent extends Event {
		public static final EventType<ComponentListItemEvent> ROOT_EVENT = new EventType<>(Event.ANY, "COMPONENTLISTITEM_ROOT_EVENT");
		public static final EventType<ComponentListItemEvent> ON_ITEM_CLICK = new EventType<>(ROOT_EVENT, "ON_ITEM_CLICK");

		private final String typeID;

		public ComponentListItemEvent(EventType<ComponentListItemEvent> eventType, String typeID) {
			super(eventType);
			this.typeID = typeID;
		}

		public String getTypeID() {
			return this.typeID;
		}
	}

}
