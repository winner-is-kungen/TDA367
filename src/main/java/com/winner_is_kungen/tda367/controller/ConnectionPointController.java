package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.view.canvas.ConnectionPoint;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * Controller for ConnectionPoint
 * Handles mouse inputs
 */
class ConnectionPointController extends ConnectionPoint {

	final int channel;
	private final String componentID;
	final ConnectionPointType ioType;

	ConnectionPointController(int channel, String componentID, ConnectionPointType ioType) {
		this.channel = channel;
		this.componentID = componentID;
		this.ioType = ioType;

		this.setOnMousePressed(mouseEvent -> {
			mouseEvent.consume();
			onClick();
		});
	}

	private void onClick() {
		this.saveColor();
		this.changeColor(ConnectorColor.ACTIVE_LOW);
		ConnectionPointEvent event = new ConnectionPointEvent(ConnectionPointEvent.CONNECTION_START_EVENT, this);
		fireEvent(event);
	}

	String getComponentID() {
		return componentID;
	}

	public enum ConnectionPointType {
		INPUT, OUTPUT
	}

	static class ConnectionPointEvent extends Event {
		static final EventType<ConnectionPointEvent> ROOT_EVENT = new EventType<>(Event.ANY, "ConnectionPointController_ROOT_EVENT");
		static final EventType<ConnectionPointEvent> CONNECTION_START_EVENT = new EventType<>(ROOT_EVENT, "Connection_Start");

		private final ConnectionPointController connectionPointController;

		ConnectionPointEvent(EventType<ConnectionPointEvent> eventType, ConnectionPointController cpc) {
			super(eventType);
			this.connectionPointController = cpc;
		}

		ConnectionPointController getConnectionPoint() {
			return this.connectionPointController;
		}

	}
}
