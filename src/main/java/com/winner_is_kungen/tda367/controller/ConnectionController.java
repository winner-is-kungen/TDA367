package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.view.canvas.Connection;
import com.winner_is_kungen.tda367.view.canvas.ConnectionPoint;
import javafx.geometry.Point2D;

class ConnectionController extends Connection {

	private final ConnectionPointController fromCP;
	private final ConnectionPointController toCP;

	/**
	 * A controller for the Connection class.
	 * this takes care of translating the position of ConnectionPoints into coords for javafx Line

	 * @param fromCP from ConectionPoint
	 * @param toCP   to ConnectionPoint
	 */
	ConnectionController(ConnectionPointController fromCP, ConnectionPointController toCP) {
		this.fromCP = fromCP;
		this.toCP = toCP;

		this.fromCP.changeColor(ConnectionPoint.ConnectorColor.DEFAULT);
		this.toCP.changeColor(ConnectionPoint.ConnectorColor.DEFAULT);
	}

	/**
	 * Regenerates new Point2D for the javafx Line from the two ConnectionPointController classes
	 * @param offset The offset from the infiniteCanvas for panning the camera
	 */
	 void updateConnection(Point2D offset) {

		// Converts local position of Connection point to coords in IniniteCanvas
		// Since the connectionPoints are under Blueprint -> Component -> Vbox -> connectionPoint

		Point2D origin = fromCP.getParent().getParent().getParent().localToParent(fromCP.getParent().localToParent(fromCP.localToParent(Point2D.ZERO)));
		Point2D dest = toCP.getParent().getParent().getParent().localToParent(toCP.getParent().localToParent(toCP.localToParent(Point2D.ZERO)));

		setOrigin(origin.subtract(offset));
		setDest(dest.subtract(offset));

		applyShape();
		applyOffset();

	}
}
