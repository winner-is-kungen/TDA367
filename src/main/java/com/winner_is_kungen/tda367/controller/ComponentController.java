package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.Position;
import com.winner_is_kungen.tda367.model.util.EventBusEvent;
import com.winner_is_kungen.tda367.view.canvas.InfiniteCanvas;
import com.winner_is_kungen.tda367.view.canvas.InfiniteCanvasBlock;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class ComponentController extends InfiniteCanvasBlock {
	/**
	 * The image in the FXML view.
	 */
	@FXML
	private ImageView image;

	/**
	 * The model of the Component this displays.
	 */
	private final Component model;

	public ComponentController(Component model, Image imageSrc) {
		// FXML setup
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Component.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		image.setImage(imageSrc);
		image.fitHeightProperty().bind(this.heightProperty());
		image.fitWidthProperty().bind(this.widthProperty());

		// Model setup
		this.model = model;

		setCoordinateX(this.model.getPosition().getX());
		setCoordinateY(this.model.getPosition().getY());

		setSizeX(5);

		int maxNrConnections = Math.max(this.model.getNrInputs(), this.model.getNrOutputs());
		setSizeY(5 + 2 * (maxNrConnections - 1));

		// Controller setup
		InfiniteCanvas.addCoordinateListener(this, this::onCoordinateChange);
		this.model.getPosition().getEventBus().addListener(Position.eventPosition, this::onPositionChange);
	}

	/**
	 * Gets the model Component this Controller is based on.
	 *
	 * @return The model Component this Controller is based on.
	 */
	public Component getModel() {
		return model;
	}

	/**
	 * Used to keep the models coordinates updated with the changes from the UI.
	 */
	private void onCoordinateChange() {
		model.getPosition().setX(getCoordinateX());
		model.getPosition().setY(getCoordinateY());
	}

	/**
	 * Used to keep the UIs coordinates updated with the changes from the model.
	 */
	private void onPositionChange(EventBusEvent<Position.PositionEvent> event) {
		if (event.getMessage().isXChange()) {
			setCoordinateX(model.getPosition().getX());
		}
		if (event.getMessage().isYChange()) {
			setCoordinateY(model.getPosition().getY());
		}
	}
}