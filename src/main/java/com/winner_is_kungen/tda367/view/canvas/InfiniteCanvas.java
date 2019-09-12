package com.winner_is_kungen.tda367.view.canvas;

import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Region;

import java.util.ArrayList;

public class InfiniteCanvas extends Region {
	private static final double zoomMultiplier = 0.025d;
	private static final double zoomBase = 2.0d;

	private final Canvas canvas = new Canvas();
	private final GraphicsContext graphics = canvas.getGraphicsContext2D();

	private final ArrayList<IRenderable> elements = new ArrayList<IRenderable>();

	private double offsetX = 0;
	private double offsetY = 0;
	private double zoomLevel = 1.0d;

	/** Used for calculating the dela X when middle mouse dragging the canvas. */
	private double mouseDragLastX = 0;
	/** Used for calculating the dela Y when middle mouse dragging the canvas. */
	private double mouseDragLastY = 0;

	public InfiniteCanvas() {
		this.getChildren().add(canvas);

		this.heightProperty().addListener(this::onSizeUpdate);
		this.widthProperty().addListener(this::onSizeUpdate);

		this.setOnMousePressed(this::onMousePressed);
		this.setOnMouseDragged(this::onMouseDragged);
		this.setOnScroll(this::onScroll);

		// Just an test object. To be removed.
		addRenderable(new IRenderable() {
			@Override
			public void render(GraphicsContext gc, double offsetX, double offsetY, double zoom) {
				gc.beginPath();
				gc.moveTo(offsetX + (10 + 5) * zoom, offsetY + (10 + 5) * zoom);
				gc.lineTo(offsetX + (10 - 5) * zoom, offsetY + (10 + 5) * zoom);
				gc.lineTo(offsetX + (10 - 5) * zoom, offsetY + (10 - 5) * zoom);
				gc.lineTo(offsetX + (10 + 5) * zoom, offsetY + (10 - 5) * zoom);
				gc.lineTo(offsetX + (10 + 5) * zoom, offsetY + (10 + 5) * zoom);
				gc.stroke();
			}
		});
	}

	/**
	 * Clears the canvas and redraws all the elements.
	 */
	private void render() {
		graphics.clearRect(0, 0, this.getWidth(), this.getHeight());

		for (IRenderable element : elements) {
			element.render(graphics, offsetX, offsetY, zoomLevel);
		}
	}

	/**
	 * Used for initializing the middle mouse dragging of the canvas (manipulating the offsets).
	 */
	private void onMousePressed(MouseEvent event) {
		if (event.getButton() == MouseButton.MIDDLE) {
			mouseDragLastX = event.getX();
			mouseDragLastY = event.getY();
		}
	}

	/**
	 * Used in each step of the middle mouse dragging of the canvas (manipulating the offsets).
	 */
	private void onMouseDragged(MouseEvent event) {
		if (event.getButton() == MouseButton.MIDDLE) {
			offsetX = offsetX + (event.getX() - mouseDragLastX);
			mouseDragLastX = event.getX();
			offsetY = offsetY + (event.getY() - mouseDragLastY);
			mouseDragLastY = event.getY();
			render();
		}
	}

	/**
	 * Used to zoom the canvas (manipulating the zoomLevel).
	 */
	private void onScroll(ScrollEvent event) {
		zoomLevel *= Math.pow(zoomBase, event.getDeltaY() * zoomMultiplier);
		render();
	}

	/**
	 * Adds an element to be rendered on the canvas.
	 * @param renderable The new element.
	 */
	public void addRenderable(IRenderable renderable) {
		elements.add(renderable);
	}

	/**
	 * Removes an element so that it's no longer rendered on the canvas.
	 * @param renderable The element to be removed.
	 */
	public void removeRenderable(IRenderable renderable) {
		elements.remove(renderable);
	}

	/**
	 * Resizes the canvas to cover the whole area and re redraws it.
	 */
	private void onSizeUpdate(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
		canvas.setHeight(this.getHeight());
		canvas.setWidth(this.getWidth());

		render();
	}
}