package com.winner_is_kungen.tda367.view.connection;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

/**
 * Visual representation of a connection between two components
 */
public class Connection extends Line {
	private double originX;
	private double originY;
	private double destX;
	private double destY;

	protected void setOrigin(Point2D origin) {
		this.originX = origin.getX();
		this.originY = origin.getY();
	}

	protected void setDest(Point2D dest) {
		this.destX = dest.getX();
		this.destY = dest.getY();
	}

	protected void applyShape() {
		setStartX(this.originX);
		setStartY(this.originY);
		setEndX(this.destX);
		setEndY(this.destY);
	}

	/**
	 * Moves the line object to the correct position
	 */
	protected void applyOffset() {
		double lineOffsetX;
		double lineOffsetY;

		lineOffsetX = Math.min(originX, destX);
		lineOffsetY = Math.min(originY, destY);

		setTranslateX(lineOffsetX);
		setTranslateY(lineOffsetY);
	}
}
