package com.winner_is_kungen.tda367.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.io.IOException;

public class MenuBarController extends MenuBar {

	private EventHandler<MenuItemEvent> onItemClickEventHandler;

	@FXML
	private MenuItem newFile;
	@FXML
	private MenuItem saveFile;
	@FXML
	private MenuItem exit;


	public MenuBarController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MenuBar.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		newFile.setOnAction(event -> {
			Event ev = new MenuItemEvent(MenuItemEvent.ON_ITEM_CLICK, "newFile");
			fireEvent(ev);
		});

		exit.setOnAction(event -> {
			Event ev = new MenuItemEvent(MenuItemEvent.ON_ITEM_CLICK, "exit");
			fireEvent(ev);
		});

		saveFile.setOnAction(event -> {
			Event ev = new MenuItemEvent(MenuItemEvent.ON_ITEM_CLICK, "saveFile");
			fireEvent(ev);
		});

	}


	public void setOnItemClick(EventHandler<MenuItemEvent> value) {
		if (onItemClickEventHandler != null) {
			removeEventHandler(MenuItemEvent.ON_ITEM_CLICK, onItemClickEventHandler);
		}

		onItemClickEventHandler = value;
		addEventHandler(MenuItemEvent.ON_ITEM_CLICK, value);
	}

	public EventHandler<MenuItemEvent> getOnItemClick() {
		return onItemClickEventHandler;
	}

	public static class MenuItemEvent extends Event {

		public static final EventType<MenuItemEvent> ROOT_EVENT = new EventType<>(Event.ANY, "MENUITEM_ROOT_EVENT");
		public static final EventType<MenuItemEvent> ON_ITEM_CLICK = new EventType<>(ROOT_EVENT, "ON_ITEM_CLICK");

		private final String menuItem;

		public MenuItemEvent(EventType<MenuItemEvent> eventType, String menuItem) {
			super(eventType);
			this.menuItem = menuItem;
		}

		public String getMenuItem() {
			return menuItem;
		}
	}
}
