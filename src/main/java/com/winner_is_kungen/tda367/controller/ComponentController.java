package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.Position;
import com.winner_is_kungen.tda367.model.util.EventBusEvent;
import com.winner_is_kungen.tda367.view.canvas.InfiniteCanvas;
import com.winner_is_kungen.tda367.view.canvas.InfiniteCanvasBlock;
import com.winner_is_kungen.tda367.controller.ConnectionPointController.ConnectionPointType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class ComponentController extends InfiniteCanvasBlock {
	/** The image in the FXML view. */
	@FXML
	private ImageView image;

	@FXML
	private VBox input_connections;

	@FXML
	private VBox output_connections;

	/** The model of the Component this displays. */
	private final Component model;
	private final BlueprintController parentBlueprint;
	private ConnectionPointController[] inputs;
	private ConnectionPointController[] outputs;

	public ComponentController(BlueprintController bpc,Component model,Image imageSrc) {
		// FXML setup
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Component.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		image.setImage(imageSrc);

		this.parentBlueprint = bpc;

		// Model setup
		this.model = model;

		setCoordinateX(this.model.getPosition().getX());
		setCoordinateY(this.model.getPosition().getY());

		setSizeX(10); // Currently hard coded component size.

		int maxNrConnections = Math.max(this.model.getNrInputs(), this.model.getNrOutputs());
		setSizeY(5 + 2 * (maxNrConnections - 1));

		int nrInputs = this.model.getNrInputs();
		inputs = new ConnectionPointController[nrInputs];
		for(int i = 0; i != nrInputs;i++){
			inputs[i] = new ConnectionPointController(this,i, ConnectionPointType.INPUT);
			input_connections.getChildren().add(inputs[i]);
		}

		int nrOutputs = this.model.getNrOutputs();
		outputs = new ConnectionPointController[nrOutputs];
		for(int i = 0; i != nrOutputs;i++){
			outputs[i] = new ConnectionPointController(this,i,ConnectionPointType.OUTPUT);
			output_connections.getChildren().add(outputs[i]);
		}

		// Controller setup
		InfiniteCanvas.addCoordinateListener(this, this::onCoordinateChange);
		this.model.getPosition().getEventBus().addListener(Position.eventPosition, this::onPositionChange);
	}

	/**
	 * Gets the model Component this Controller is based on.
	 * @return The model Component this Controller is based on.
	 */
	public Component getModel() {
		return model;
	}

	public String getID(){
		return model.getId();
	}

	/**
	 * Used to keep the models coordinates updated with the changes from the UI.
	 */
	private void onCoordinateChange() {
		model.getPosition().setX(getCoordinateX());
		model.getPosition().setY(getCoordinateY());
	}

	protected void onConnectionPointPressed(ConnectionPointController cpc){
		this.parentBlueprint.startConnection(cpc);
	}

	public ConnectionPointController getInputConnectionPoint(int i){
		return inputs[i];
	}

	public ConnectionPointController getOutputConnectionPoint(int i){
		return outputs[i];
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