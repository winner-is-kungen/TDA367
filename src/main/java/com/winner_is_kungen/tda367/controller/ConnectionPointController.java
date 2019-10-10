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

	final int channel;
	final ConnectionPointType ioType;
	final ComponentController component;
	private final ConnectionPointListener connectionPointListener;

	ConnectionPointController(ComponentController component, int channel, ConnectionPointType ioType) {
		super();
		this.channel = channel;
		this.ioType = ioType;
		this.component = component;
		this.connectionPointListener = component.getConnectionPointListener();

		this.setOnMousePressed(mouseEvent -> {
			mouseEvent.consume();
			onClick();
		});
	}

	private void onClick() {
		this.changeColor(ConnectorColor.ACTIVE);
		connectionPointListener.startConnection(this);
	}
}
