package com.winner_is_kungen.tda367.view.canvas;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
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

	private Point2D offset = new Point2D(0, 0);
	private double zoomLevel = 1.0d;

	/** Used for calculating the dela X,Y when middle mouse dragging the canvas. */
	private Point2D mouseDragLast = new Point2D(0, 0);

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
			public void render(GraphicsContext gc, Point2D offset, double zoom) {
				gc.beginPath();
				gc.moveTo(offset.getX() + (10 + 10) * zoom, offset.getY() + (10 + 10) * zoom);
				gc.lineTo(offset.getX() + (10 - 10) * zoom, offset.getY() + (10 + 10) * zoom);
				gc.lineTo(offset.getX() + (10 - 10) * zoom, offset.getY() + (10 - 10) * zoom);
				gc.lineTo(offset.getX() + (10 + 10) * zoom, offset.getY() + (10 - 10) * zoom);
				gc.lineTo(offset.getX() + (10 + 10) * zoom, offset.getY() + (10 + 10) * zoom);
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
			element.render(graphics, offset, zoomLevel);
		}
	}

	/**
	 * Used for initializing the middle mouse dragging of the canvas (manipulating the offsets).
	 */
	private void onMousePressed(MouseEvent event) {
		if (event.getButton() == MouseButton.MIDDLE) {
			mouseDragLast = new Point2D(event.getX(), event.getY());
		}
	}

	/**
	 * Used in each step of the middle mouse dragging of the canvas (manipulating the offsets).
	 */
	private void onMouseDragged(MouseEvent event) {
		if (event.getButton() == MouseButton.MIDDLE) {
			offset = offset.add(event.getX() - mouseDragLast.getX(), event.getY() - mouseDragLast.getY());
			mouseDragLast = new Point2D(event.getX(), event.getY());
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
	 * Resizes the canvas to cover the whole area and redraws it.
	 */
	private void onSizeUpdate(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
		canvas.setHeight(this.getHeight());
		canvas.setWidth(this.getWidth());

		render();
	}

	/**
	 * Transforms a Point on this node to a Point in the virtual coordinate system.
	 * @param point The local Point.
	 * @return A Point in the local coordinate system.
	 */
	public Point2D localToCoordinates(Point2D point) {
		return point.multiply(zoomLevel).add(offset);
	}
	/**
	 * Transforms a Point on this node to a Point in the virtual coordinate system.
	 * @param x The local x.
	 * @param y The local y.
	 * @return A Point in the local coordinate system.
	 */
	public Point2D localToCoordinates(double x, double y) {
		return localToCoordinates(new Point2D(x, y));
	}
}