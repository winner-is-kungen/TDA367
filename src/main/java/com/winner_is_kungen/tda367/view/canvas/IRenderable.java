package com.winner_is_kungen.tda367.view.canvas;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public interface IRenderable {
	void render(GraphicsContext gc, Point2D offset, double zoom);
}