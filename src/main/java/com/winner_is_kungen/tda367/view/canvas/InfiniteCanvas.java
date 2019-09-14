package com.winner_is_kungen.tda367.view.canvas;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class InfiniteCanvas extends Pane {
	//#region Child properties
	private static final String COORDINATE_X = "infinite-canvas-coordinate-x";
	private static final String COORDINATE_Y = "infinite-canvas-coordinate-y";
	private static final String SIZE_X = "infinite-canvas-size-x";
	private static final String SIZE_Y = "infinite-canvas-size-y";

	/**
	 * Adds or updates a property of a node, and requests a layout update from its parent.
	 * @param node  The targeted node.
	 * @param key   The property's key.
	 * @param value The new value of the property.
	 */
	private static void setProperty(Node node, Object key, Object value) {
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
	 * @param node The targeted node.
	 * @param key  The property's key.
	 * @return The property's value.
	 */
	private static Object getProperty(Node node, Object key) {
		if (node.hasProperties()) {
			return node.getProperties().get(key);
		}
		return null;
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
		return (Integer) getProperty(node, COORDINATE_X);
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
		return (Integer)getProperty(node, COORDINATE_Y);
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
		return (Integer)getProperty(node, SIZE_X);
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
		return (Integer)getProperty(node, SIZE_Y);
	}

	//#endregion Child properties

	/**
	 * Creates a new InfiniteCanvas.
	 */
	public InfiniteCanvas() {
		super();
	}
	/**
	 * Creates a new InfiniteCanvas with predefined children.
	 * @param children The children of the new InfiniteCanvas.
	 */
	public InfiniteCanvas(Node... children) {
		super(children);
	}

	@Override
	public void layoutChildren() {
		for (Node child : getManagedChildren()) {
			child.relocate(getCoordinateX(child), getCoordinateY(child));

			if (child.isResizable()) {
				child.resize(getSizeX(child), getSizeY(child));
			}
			else {
				child.autosize();
			}
		}
	}
}