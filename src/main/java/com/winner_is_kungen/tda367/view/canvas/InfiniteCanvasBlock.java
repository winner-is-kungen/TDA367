package com.winner_is_kungen.tda367.view.canvas;

import javafx.beans.DefaultProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

@DefaultProperty("content")
public class InfiniteCanvasBlock extends AnchorPane {
	private final ObjectProperty<Node> content = new SimpleObjectProperty<Node>(this, "content");

	/** The starting location of the mouse in screen pixels during a drag. */
	private Point2D mouseDragStart;
	/** The starting location of this block in coordinates during a drag. */
	private Point2D coordinateDragStart;

	public InfiniteCanvasBlock() {
		super();

		setOnMousePressed(this::onMousePressed);
		setOnMouseDragged(this::onMouseDragged);
	}
	public InfiniteCanvasBlock(Node content) {
		this();
		setContent(content);
	}

	//#region Dragging
	/**
	 * Used to initialize a drag of this block.
	 */
	private void onMousePressed(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			Bounds boundsInScreen = localToScreen(getBoundsInLocal());
			//mouseDragStart = new Point2D(boundsInScreen.getCenterX(), boundsInScreen.getCenterY());
			//coordinateDragStart = new Point2D(getCoordinateX(), getCoordinateY());
		}
	}
	/**
	 * Called in each step of the drag of this block. Updates this block location according to how far it's dragged.
	 */
	private void onMouseDragged(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			Point2D mouse = new Point2D(event.getScreenX(), event.getScreenY());
			Point2D delta = mouse.subtract(mouseDragStart);

			Bounds layoutBounds = getLayoutBounds();
			Point2D coordinatesMoved = new Point2D(
					delta.getX() / (layoutBounds.getWidth() / getSizeX()) / getScaleX(),
					delta.getY() / (layoutBounds.getHeight() / getSizeY()) / getScaleY()
			);

			setCoordinateX((int)Math.round(coordinateDragStart.getX() + coordinatesMoved.getX()));
			setCoordinateY((int)Math.round(coordinateDragStart.getY() + coordinatesMoved.getY()));
		}
	}
	//#endregion Dragging

	//#region InfiniteCanvas properties
	public int getCoordinateX() {
		return InfiniteCanvas.getCoordinateX(this);
	}
	protected void setCoordinateX(int value) {
		InfiniteCanvas.setCoordinateX(this, value);
	}
	public int getCoordinateY() {
		return InfiniteCanvas.getCoordinateY(this);
	}
	protected void setCoordinateY(int value) {
		InfiniteCanvas.setCoordinateY(this, value);
	}
	public int getSizeX() {
		return InfiniteCanvas.getSizeX(this);
	}
	protected void setSizeX(int value) {
		InfiniteCanvas.setSizeX(this, value);
	}
	public int getSizeY() {
		return InfiniteCanvas.getSizeY(this);
	}
	protected void setSizeY(int value) {
		InfiniteCanvas.setSizeY(this, value);
	}
	//#endregion InfiniteCanvas properties

	//#region Content handling
	public final Node getContent() {
		return content.get();
	}
	public final void setContent(Node value) {
		Node previous = getContent();
		if (previous != null) {
			getChildren().remove(previous);
		}

		contentProperty().set(value);

		if (value != null) {
			getChildren().add(value);

			AnchorPane.setLeftAnchor(value, 0.0d);
			AnchorPane.setBottomAnchor(value, 0.0d);
			AnchorPane.setRightAnchor(value, 0.0d);
			AnchorPane.setTopAnchor(value, 0.0d);
		}
	}
	public final ObjectProperty<Node> contentProperty() {
		return content;
	}
	//#endregion Content handling
}