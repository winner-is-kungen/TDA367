package com.winner_is_kungen.tda367.view.canvas;

import javafx.collections.MapChangeListener;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class InfiniteCanvas extends Pane {
	//#region Child properties
	private static final String COORDINATE_X = "infinite-canvas-coordinate-x";
	private static final String COORDINATE_Y = "infinite-canvas-coordinate-y";
	private static final String SIZE_X = "infinite-canvas-size-x";
	private static final String SIZE_Y = "infinite-canvas-size-y";

	private static final List<String> COORDINATES = List.of(COORDINATE_X, COORDINATE_Y);
	private static final List<String> SIZES = List.of(SIZE_X, SIZE_Y);

	/**
	 * Adds or updates a property of a node, and requests a layout update from its parent.
	 * @param node  The targeted node.
	 * @param key   The property's key.
	 * @param value The new value of the property.
	 * @param <T>   The type of the value.
	 */
	private static <T> void setProperty(Node node, Object key, T value) {
		if (value == null) {
			node.getProperties().remove(key);
		} else {
			node.getProperties().put(key, value);
		}
		if (node.getParent() != null) {
			node.getParent().requestLayout();
		}
	}
	/**
	 * Gets a property from a node.
	 * @param node     The targeted node.
	 * @param key      The property's key.
	 * @param fallback A fallback value in case the provide node doesn't have any value for the provided key.
	 * @param <T>      The type of the value.
	 * @return The property's value.
	 */
	private static <T> T getProperty(Node node, Object key, T fallback) {
		if (node.hasProperties()) {
			Object value = node.getProperties().get(key);
			if (value != null) {
				return (T)value;
			}
		}
		return fallback;
	}

	/**
	 * Sets the x coordinate of a node and requests a layout update from its parent.
	 * @param node  The targeted node.
	 * @param value The new x coordinate.
	 */
	public static void setCoordinateX(Node node, Integer value) {
		setProperty(node, COORDINATE_X, value);
	}
	/**
	 * Gets the x coordinate of a node.
	 * @param node The targeted node.
	 * @return The x coordinate of the node.
	 */
	public static Integer getCoordinateX(Node node) {
		return getProperty(node, COORDINATE_X, 0);
	}
	/**
	 * Sets the y coordinate of a node and requests a layout update from its parent.
	 * @param node  The targeted node.
	 * @param value The new y coordinate.
	 */
	public static void setCoordinateY(Node node, Integer value) {
		setProperty(node, COORDINATE_Y, value);
	}
	/**
	 * Gets the y coordinate of a node.
	 * @param node The targeted node.
	 * @return The y coordinate of the node.
	 */
	public static Integer getCoordinateY(Node node) {
		return getProperty(node, COORDINATE_Y, 0);
	}

	/**
	 * Sets the x size of a node and requests a layout update from its parent.
	 * @param node  The targeted node.
	 * @param value The new x size.
	 */
	public static void setSizeX(Node node, Integer value) {
		setProperty(node, SIZE_X, value);
	}
	/**
	 * Gets the x size of a node.
	 * @param node The targeted node.
	 * @return The x size of the node.
	 */
	public static Integer getSizeX(Node node) {
		return getProperty(node, SIZE_X, 0);
	}
	/**
	 * Sets the y size of a node and requests a layout update from its parent.
	 * @param node  The targeted node.
	 * @param value The new y size.
	 */
	public static void setSizeY(Node node, Integer value) {
		setProperty(node, SIZE_Y, value);
	}
	/**
	 * Gets the x size of a node.
	 * @param node The targeted node.
	 * @return The x size of the node.
	 */
	public static Integer getSizeY(Node node) {
		return getProperty(node, SIZE_Y, 0);
	}

	/**
	 * Adds a listener that is activated by changes to one or more properties.
	 * @param node     The node the listener should be attached to.
	 * @param keys     The keys for the properties the listener should be activated by.
	 * @param listener The listener itself.
	 */
	private static void addPropertyListener(Node node, List<?> keys, InfiniteCanvasPropertyListener listener) {
		node.getProperties().addListener((MapChangeListener<Object, Object>) change -> {
			if (keys.contains(change.getKey())) {
				listener.change();
			}
		});
	}

	/**
	 * Adds a listener that is activated by changes to a nodes coordinate properties.
	 * @param node     The node the listener should be attached to.
	 * @param listener The listener itself.
	 */
	public static void addCoordinateListener(Node node, InfiniteCanvasPropertyListener listener) {
		addPropertyListener(node, COORDINATES, listener);
	}
	/**
	 * Adds a listener that is activated by changes to a nodes size properties.
	 * @param node     The node the listener should be attached to.
	 * @param listener The listener itself.
	 */
	public static void addSizeListener(Node node, InfiniteCanvasPropertyListener listener) {
		addPropertyListener(node, SIZES, listener);
	}
	//#endregion Child properties

	/** A list of all the different zoom factors. */
	private static final double[] zoomPoints = { 0.125d, 0.25d, 0.5d, 1.0d, 1.5d, 2.0d };
	/** The starting zoom level, should probably point to 1.0d. */
	private static final int zoomPointBase = 3;

	private static final double coordinateSize = 10.0d;

	/** The offset in pixels. */
	protected Point2D offset = new Point2D(0, 0);
	/** The local position of the last step in dragging the mouse. */
	private Point2D lastMouseDrag;

	/** The index of the current zoom factor in the `zoomPoints` array.  */
	private int zoomPoint = zoomPointBase;
	private double getZoomFactor() {
		return zoomPoints[zoomPoint];
	}

	/**
	 * Creates a new InfiniteCanvas.
	 */
	public InfiniteCanvas() {
		super();

		setOnMousePressed(this::onMousePressed);
		setOnMouseDragged(this::onMouseDragged);
		setOnScroll(this::onScroll);

		clipChildren();
	}

	/**
	 * Creates a rectangle and keeps it's size in sync with the canvas' size.
	 * The rectangle is used to clip the children when they're outside of the canvas.
	 */
	private void clipChildren() {
		Rectangle clippingRectangle = new Rectangle();
		setClip(clippingRectangle);

		layoutBoundsProperty().addListener((ob, oldValue, newValue) -> {
			clippingRectangle.setWidth(newValue.getWidth());
			clippingRectangle.setHeight(newValue.getHeight());
		});
	}

	/**
	 * Used to initialize mouse dragging.
	 */
	private void onMousePressed(MouseEvent event) {
		if (event.getButton() == MouseButton.MIDDLE) {
			lastMouseDrag = new Point2D(event.getX(), event.getY());
		}
	}
	/**
	 * Called in each step of mouse dragging to update the offset (and layout accordingly).
	 */
	private void onMouseDragged(MouseEvent event) {
		if (event.getButton() == MouseButton.MIDDLE) {
			Point2D nextMouseDrag = new Point2D(event.getX(), event.getY());
			Point2D delta = nextMouseDrag.subtract(lastMouseDrag);
			offset = offset.add(delta);
			lastMouseDrag = nextMouseDrag;

			requestLayout();
		}
	}

	private void onScroll(ScrollEvent event) {
		double zoomFactorBefore = getZoomFactor();
		Point2D offsetBefore = offset;
		Point2D mouse = new Point2D(event.getX(), event.getY());

		zoomPoint = Math.max(0, Math.min(zoomPoint + (int)(event.getDeltaY() / Math.abs(event.getDeltaY())), zoomPoints.length - 1));

		offset = mouse.subtract(mouse.subtract(offsetBefore).multiply(getZoomFactor() / zoomFactorBefore));

		requestLayout();
	}

	@Override
	public void layoutChildren() {
		for (Node child : getManagedChildren()) {
			child.setScaleX(getZoomFactor());
			child.setScaleY(getZoomFactor());

			Bounds layoutBounds = child.getLayoutBounds();
			Point2D originalDimensions = new Point2D(layoutBounds.getWidth(), layoutBounds.getHeight());
			Point2D zoomedDimensions = originalDimensions.multiply(getZoomFactor());
			child.relocate(
					offset.getX() + getCoordinateX(child) * coordinateSize * getZoomFactor() + (zoomedDimensions.getX() - originalDimensions.getX()) / 2.0d,
					offset.getY() + getCoordinateY(child) * coordinateSize * getZoomFactor() + (zoomedDimensions.getY() - originalDimensions.getY()) / 2.0d
			);

			if (child.isResizable()) {
				child.resize(getSizeX(child) * coordinateSize, getSizeY(child) * coordinateSize);
			}
			else {
				child.autosize();
			}
		}
	}
}