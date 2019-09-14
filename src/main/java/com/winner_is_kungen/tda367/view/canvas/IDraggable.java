package com.winner_is_kungen.tda367.view.canvas;

import javafx.geometry.Point2D;

public interface IDraggable extends IHittable {
	/**
	 * Called each step in a drag sequence.
	 * @param delta The delta since last call in coordinates.
	 */
	void drag(Point2D delta);
}