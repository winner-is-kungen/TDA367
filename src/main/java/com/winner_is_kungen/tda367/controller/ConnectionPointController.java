package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.view.canvas.ConnectionPoint;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Controller for ConnectionPoint
 * Handles mouse inputs
 */
public class ConnectionPointController extends ConnectionPoint {

	public enum ConnectionPointType {
		INPUT, OUTPUT
	}

	int channel;
	ConnectionPointType ioType;
	ComponentController component;

	ConnectionPointController(ComponentController component, int channel, ConnectionPointType ioType) {
		super();
		this.channel = channel;
		this.ioType = ioType;
		this.component = component;

		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				mouseEvent.consume();
				onClick();
			}
		});
	}

	private void onClick() {
		this.changeColor(ConnectorColor.ACTIVE);
		component.onConnectionPointPressed(this);
	}
}
