package com.winner_is_kungen.tda367.view.canvas;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Region;

import java.util.ArrayList;

public class InfiniteCanvas extends Region {
	/** A list of all the different zoom levels. */
	private static final double[] zoomPoints = { 0.125d, 0.25d, 0.5d, 1.0d, 1.5d, 2.0d };
	/** The starting zoom level, should probably point to 1.0d. */
	private static final int zoomPointBase = 3;

	private final Canvas canvas = new Canvas();
	private final GraphicsContext graphics = canvas.getGraphicsContext2D();

	private final ArrayList<IRenderable> elements = new ArrayList<IRenderable>();

	private Point2D offset = new Point2D(0, 0);

	/** The index of the current zoom level in the `zoomPoints` array. */
	private int zoomPoint = zoomPointBase;
	private double getZoomLevel() {
		return zoomPoints[zoomPoint];
	}

	/** Used for calculating the delta X,Y when middle mouse dragging the canvas. */
	private Point2D mouseDragLast = new Point2D(0, 0);

	public InfiniteCanvas() {
		this.getChildren().add(canvas);

		this.heightProperty().addListener(this::onSizeUpdate);
		this.widthProperty().addListener(this::onSizeUpdate);

		this.setOnMouseClicked(this::onMouseClicked);
		this.setOnMousePressed(this::onMousePressed);
		this.setOnMouseDragged(this::onMouseDragged);
		this.setOnScroll(this::onScroll);

		// Just an test object. To be removed.
		addRenderable(new IRenderable() {
			@Override
			public void render(GraphicsContext gc, Point2D offset, double zoom) {
				gc.strokeRect(offset.getX() + 0 * zoom, offset.getY() + 0 * zoom, 10 * zoom,10 * zoom);
			}
		});
		addRenderable(new IRenderable() {
			@Override
			public void render(GraphicsContext gc, Point2D offset, double zoom) {
				gc.strokeRect(offset.getX() + 100 * zoom, offset.getY() + 0 * zoom, 10 * zoom,10 * zoom);
			}
		});
		addRenderable(new IRenderable() {
			@Override
			public void render(GraphicsContext gc, Point2D offset, double zoom) {
				gc.strokeRect(offset.getX() + 0 * zoom, offset.getY() + 100 * zoom, 10 * zoom,10 * zoom);
			}
		});
		addRenderable(new IHittable() {
			@Override
			public void render(GraphicsContext gc, Point2D offset, double zoom) {
				gc.strokeRect(offset.getX() + 100 * zoom, offset.getY() + 100 * zoom, 10 * zoom,10 * zoom);
			}

			@Override
			public void hit(Point2D coordinates) {
				System.out.println("Coordinates: " + coordinates);
			}

			@Override
			public Rectangle2D getHitbox() {
				return new Rectangle2D(100, 100, 10, 10);
			}
		});
	}

	/**
	 * Clears the canvas and redraws all the elements.
	 */
	private void render() {
		graphics.clearRect(0, 0, this.getWidth(), this.getHeight());

		for (IRenderable element : elements) {
			element.render(graphics, offset, getZoomLevel());
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
	 * Used to zoom the canvas (manipulating the zoomLevel and the offset).
	 */
	private void onScroll(ScrollEvent event) {
		double zoomLevelBefore = getZoomLevel();
		Point2D offsetBefore = offset;
		Point2D cursor = new Point2D(event.getX(), event.getY());

		zoomPoint = Math.max(0, Math.min(zoomPoint + (int)(event.getDeltaY() / Math.abs(event.getDeltaY())), zoomPoints.length - 1));

		offset = cursor.subtract(cursor.subtract(offsetBefore).multiply(getZoomLevel() / zoomLevelBefore));
		render();
	}

	/**
	 * Used to detect and forward mouse clicks to elements.
	 */
	private void onMouseClicked(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			Point2D mouseCoordinates = this.localToCoordinates(event.getX(), event.getY());

			for (IRenderable renderable : elements) {
				if (renderable instanceof IHittable) {
					IHittable hittable = (IHittable)renderable;
					Rectangle2D hitbox = hittable.getHitbox();

					if (hitbox.contains(mouseCoordinates)) {
						hittable.hit(mouseCoordinates.subtract(hitbox.getMinX(), hitbox.getMinY()));

						// Only sends the hit to one element.
						return;
					}
				}
			}
		}
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
	private Point2D localToCoordinates(Point2D point) {
		return point.subtract(offset).multiply(1.0d / getZoomLevel());
	}
	/**
	 * Transforms a Point on this node to a Point in the virtual coordinate system.
	 * @param x The local x.
	 * @param y The local y.
	 * @return A Point in the local coordinate system.
	 */
	private Point2D localToCoordinates(double x, double y) {
		return localToCoordinates(new Point2D(x, y));
	}
}