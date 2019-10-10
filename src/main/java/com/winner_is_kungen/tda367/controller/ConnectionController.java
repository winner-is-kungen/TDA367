package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Blueprint;
import com.winner_is_kungen.tda367.view.canvas.Connection;
import com.winner_is_kungen.tda367.view.canvas.ConnectionPoint;
import com.winner_is_kungen.tda367.view.canvas.InfiniteCanvas;
import javafx.geometry.Point2D;

public class ConnectionController extends Connection {

	private ConnectionPointController fromCP;
	private ConnectionPointController toCP;
	private BlueprintController bpc;

	/**
	 * A controller for the Connection class.
	 * this takes care of translating the position of ConnectionPoints into coords for javafx Line
	 *
	 * @param bpc    Parent blueprint controller, needed to factor in correct offset
	 * @param fromCP from ConectionPoint
	 * @param toCP   to ConnectionPoint
	 */
	public ConnectionController(BlueprintController bpc, ConnectionPointController fromCP, ConnectionPointController toCP) {
		super();
		this.fromCP = fromCP;
		this.toCP = toCP;
		this.bpc = bpc;

		this.fromCP.changeColor(ConnectionPoint.ConnectorColor.DEFAULT);
		this.toCP.changeColor(ConnectionPoint.ConnectorColor.DEFAULT);

		updateConnection();
	}

	/**
	 * Regenerates new Point2D for the javafx Line from the two ConnectionPointController classes
	 */
	public void updateConnection() {


		Point2D offset = bpc.getOffset();


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
