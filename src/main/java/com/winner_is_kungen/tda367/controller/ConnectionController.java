package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.view.connection.Connection;
import com.winner_is_kungen.tda367.view.connection.ConnectionPoint;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Point2D;

class ConnectionController extends Connection {

	private final ConnectionPointController fromCP;
	private final ConnectionPointController toCP;

	/**
	 * A controller for the Connection class.
	 * this takes care of translating the position of ConnectionPoints into coords for javafx Line
	 *
	 * @param fromCP from ConectionPoint
	 * @param toCP   to ConnectionPoint
	 */
	ConnectionController(ConnectionPointController fromCP, ConnectionPointController toCP) {
		this.fromCP = fromCP;
		this.toCP = toCP;

		this.fromCP.fillProperty().set(this.fromCP.getTemporarySavedColor());
		this.toCP.changeColor(ConnectionPoint.ConnectorColor.DEFAULT_LOW);

		this.setOnMousePressed(mouseEvent -> {
			mouseEvent.consume();
			onClick();
		});
	}

	/**
	 * Regenerates new Point2D for the javafx Line from the two ConnectionPointController classes
	 *
	 * @param offset The offset from the infiniteCanvas for panning the camera
	 */
	void updateConnection(Point2D offset) {

		// Converts local position of Connection point to coords in IniniteCanvas
		// Since the connectionPoints are under Blueprint -> Component -> Vbox -> connectionPoint

		Point2D origin = fromCP.localToScene(Point2D.ZERO);
		Point2D dest = toCP.localToScene(Point2D.ZERO);

		setOrigin(origin.subtract(offset));
		setDest(dest.subtract(offset));

		applyShape();
		applyOffset();

	}

	void onClick() {
		ConnectionEvent event = new ConnectionEvent(ConnectionEvent.CONNECTION_REMOVE_EVENT, this.fromCP, this.toCP);
		fireEvent(event);
	}

	static class ConnectionEvent extends Event {
		static final EventType<ConnectionEvent> ROOT_EVENT = new EventType<>(Event.ANY, "Connection root event");
		static final EventType<ConnectionEvent> CONNECTION_REMOVE_EVENT = new EventType<>(ROOT_EVENT, "Connection remove event");

		ConnectionPointController fromCP;
		ConnectionPointController toCP;

		ConnectionEvent(EventType<ConnectionEvent> eventType, ConnectionPointController fromCP, ConnectionPointController toCP) {
			super(eventType);
			this.fromCP = fromCP;
			this.toCP = toCP;
		}

		ConnectionPointController getFromCP() {
			return this.fromCP;
		}

		ConnectionPointController getToCP() {
			return this.toCP;
		}

	}
}
