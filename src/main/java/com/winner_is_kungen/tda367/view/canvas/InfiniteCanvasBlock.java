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

	private Point2D mouseDragStart;
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

	private void onMousePressed(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			mouseDragStart = new Point2D(event.getScreenX(), event.getScreenY());
			coordinateDragStart = new Point2D(getCoordinateX(), getCoordinateY());
		}
	}
	private void onMouseDragged(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			Point2D mouse = new Point2D(event.getScreenX(), event.getScreenY());
			Point2D delta = mouse.subtract(mouseDragStart);

			Bounds layoutBounds = getLayoutBounds();
			Point2D coordinatesMoved = new Point2D(
					delta.getX() / (layoutBounds.getWidth() / getSizeX()),
					delta.getY() / (layoutBounds.getHeight() / getSizeY())
			);

			setCoordinateX((int)Math.round(coordinateDragStart.getX() + coordinatesMoved.getX()));
			setCoordinateY((int)Math.round(coordinateDragStart.getY() + coordinatesMoved.getY()));
		}
	}

	//#region InfiniteCanvas properties
	private int getCoordinateX() {
		return InfiniteCanvas.getCoordinateX(this);
	}
	private void setCoordinateX(int value) {
		InfiniteCanvas.setCoordinateX(this, value);
	}
	private int getCoordinateY() {
		return InfiniteCanvas.getCoordinateY(this);
	}
	private void setCoordinateY(int value) {
		InfiniteCanvas.setCoordinateY(this, value);
	}
	private int getSizeX() {
		return InfiniteCanvas.getSizeX(this);
	}
	private void setSizeX(int value) {
		InfiniteCanvas.setSizeX(this, value);
	}
	private int getSizeY() {
		return InfiniteCanvas.getSizeY(this);
	}
	private void setSizeY(int value) {
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