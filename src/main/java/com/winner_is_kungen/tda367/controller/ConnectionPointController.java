package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.view.canvas.ConnectionPoint;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Controller for ConnectionPoint
 * Handles mouse inputs
 */
public class ConnectionPointController extends ConnectionPoint {

	final int channel;
	final String componentID;
	final ConnectionPointType ioType;
	private final ConnectionPointListener connectionPointListener;

	ConnectionPointController(int channel,String componentID, ConnectionPointType ioType, ConnectionPointListener connectionPointListener) {
		this.channel = channel;
		this.componentID = componentID;
		this.ioType = ioType;
		this.connectionPointListener = connectionPointListener;

		this.setOnMousePressed(mouseEvent -> {
			mouseEvent.consume();
			onClick();
		});
	}

	private void onClick() {
		this.changeColor(ConnectorColor.ACTIVE_LOW);
		connectionPointListener.startConnection(this);
	}

	String getComponentID(){
		return componentID;
	}

	public enum ConnectionPointType {
		INPUT, OUTPUT
	}
}
