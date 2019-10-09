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

	private EventHandler<ComponentListItemEvent> onClickEventHandler;

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

	@FXML
	private void onClick() {
		Event e = new ComponentListItemEvent(ComponentListItemEvent.ON_CLICK);
		fireEvent(e);
	}

	public void setOnClick(EventHandler<ComponentListItemEvent> value) {
		if (onClickEventHandler != null) {
			removeEventHandler(ComponentListItemEvent.ON_CLICK, onClickEventHandler);
		}

		onClickEventHandler = value;
		addEventHandler(ComponentListItemEvent.ON_CLICK, value);
	}

	public EventHandler<ComponentListItemEvent> getOnClick() {
		return onClickEventHandler;
	}

	public static class ComponentListItemEvent extends Event {
		public static final EventType<ComponentListItemEvent> ROOT_EVENT = new EventType<>(Event.ANY, "COMPONENTLISTITEM_ROOT_EVENT");
		public static final EventType<ComponentListItemEvent> ON_CLICK = new EventType<>(ROOT_EVENT, "ON_CLICK");

		public ComponentListItemEvent(EventType<ComponentListItemEvent> eventType) {
			super(eventType);
		}
	}

}
