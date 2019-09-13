package com.winner_is_kungen.tda367.view.canvas;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public interface IHittable extends IRenderable {
	/**
	 * Called when a click is registered on the canvas.
	 * @param coordinates The coordinates of the click relative to this objects hitbox.
	 */
	void hit(Point2D coordinates);

	/**
	 * Should return a rectangle for which this object want to receive hits.
	 * @return A bounding rectangle for this object in coordinates.
	 */
	Rectangle2D getHitbox();
}