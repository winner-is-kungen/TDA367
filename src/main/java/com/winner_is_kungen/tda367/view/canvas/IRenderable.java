package com.winner_is_kungen.tda367.view.canvas;

import javafx.scene.canvas.GraphicsContext;

public interface IRenderable {
	void render(GraphicsContext gc, double offsetX, double offsetY);
}