package com.winner_is_kungen.tda367.view.canvas;

import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class InfiniteCanvas extends Pane {
	private final Canvas canvas = new Canvas();
	private final GraphicsContext graphics = canvas.getGraphicsContext2D();

	public InfiniteCanvas() {
		this.getChildren().add(canvas);

		this.heightProperty().addListener(this::onSizeUpdate);
		this.widthProperty().addListener(this::onSizeUpdate);
	}

	private void render() {
		graphics.clearRect(0, 0, this.getWidth(), this.getHeight());

		graphics.beginPath();
		graphics.moveTo(0, 0);
		graphics.lineTo(this.getWidth(), this.getHeight());
		graphics.stroke();
	}

	private void onSizeUpdate(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
		canvas.setHeight(this.getHeight());
		canvas.setWidth(this.getWidth());

		this.render();
	}
}