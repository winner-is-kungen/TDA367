package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.view.canvas.Connection;
import com.winner_is_kungen.tda367.view.canvas.ConnectionPoint;
import javafx.geometry.Point2D;
import javafx.scene.Node;

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

		this.fromCP.changeColor(ConnectionPoint.ConnectorColor.DEFAULT_LOW);
		this.toCP.changeColor(ConnectionPoint.ConnectorColor.DEFAULT_LOW);
	}

	/**
	 * Regenerates new Point2D for the javafx Line from the two ConnectionPointController classes
	 *
	 * @param offset The offset from the infiniteCanvas for panning the camera
	 */
	void updateConnection(Point2D offset) {

		// Converts local position of Connection point to coords in IniniteCanvas
		// Since the connectionPoints are under Blueprint -> Component -> Vbox -> connectionPoint


		Node originBlueprint = fromCP.getParent().getParent().getParent();
		Node originVbox = fromCP.getParent();
		Point2D origin = originBlueprint.localToParent(originVbox.localToParent(fromCP.localToParent(Point2D.ZERO)));

		Node destBlueprint = toCP.getParent().getParent().getParent();
		Node destVbox = toCP.getParent();
		Point2D dest = destBlueprint.localToParent(destVbox.localToParent(toCP.localToParent(Point2D.ZERO)));

		setOrigin(origin.subtract(offset));
		setDest(dest.subtract(offset));

		applyShape();
		applyOffset();

	}
}
