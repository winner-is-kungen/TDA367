package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.LogicGates.Input;
import com.winner_is_kungen.tda367.model.ComponentListener;
import com.winner_is_kungen.tda367.model.Position;
import com.winner_is_kungen.tda367.model.util.EventBusEvent;
import com.winner_is_kungen.tda367.view.connection.ConnectionPoint;
import com.winner_is_kungen.tda367.view.canvas.InfiniteCanvas;
import com.winner_is_kungen.tda367.view.canvas.InfiniteCanvasBlock;
import com.winner_is_kungen.tda367.controller.ConnectionPointController.ConnectionPointType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.io.IOException;

public class ComponentController extends InfiniteCanvasBlock {
	/**
	 * The label in the FXML view of component.
	 */
	@FXML
	private Label componentSymbol;

	@FXML
	private VBox input_connections;

	@FXML
	private VBox output_connections;

	/**
	 * The model of the Component this displays.
	 */
	private final Component model;
	private ConnectionPointController[] inputs;
	private ConnectionPointController[] outputs;

	public ComponentController(Component model, String symbol) {
		// FXML setup
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Component.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		//this.addEventHandler();

		componentSymbol.setText(symbol);
		// Model setup
		this.model = model;

		setCoordinateX(this.model.getPosition().getX());
		setCoordinateY(this.model.getPosition().getY());

		setSizeX(10); // Currently hard coded componentX size.

		int maxNrConnections = Math.max(this.model.getNrInputs(), this.model.getNrOutputs());
		setSizeY(5 + 2 * (maxNrConnections - 1));

		// Create new ConnectionPoints for each input/output in model

		int nrInputs = this.model.getNrInputs();
		inputs = new ConnectionPointController[nrInputs];
		for (int i = 0; i != nrInputs; i++) {
			inputs[i] = new ConnectionPointController(i, this.getID(), ConnectionPointType.INPUT);
			input_connections.getChildren().add(inputs[i]);
		}

		int nrOutputs = this.model.getNrOutputs();
		outputs = new ConnectionPointController[nrOutputs];
		for (int i = 0; i != nrOutputs; i++) {
			outputs[i] = new ConnectionPointController(i, this.getID(), ConnectionPointType.OUTPUT);
			output_connections.getChildren().add(outputs[i]);
		}

		// Controller setup
		InfiniteCanvas.addCoordinateListener(this, this::onCoordinateChange);
		this.model.getPosition().getEventBus().addListener(Position.eventPosition, this::onPositionChange);

		this.setOnMouseClicked(mouseEvent -> {
			if(mouseEvent.isAltDown()) {
				mouseEvent.consume();
				if (model.getTypeId().equals("INPUT")) {
					onClick();
				}
			}
		});

		// Component Listener for outputs
		ComponentListener cl = (ComponentListener.ComponentListenerNoRecords)this::onOutputChange;

		for (int i = 0; i < this.model.getNrOutputs(); i++) {
			this.model.addListener(cl, i, i);
		}

	}


	/**
	 * Changes the color of output connection points based on the value High or Low
	 * High will change to Red and Low will change to Green
	 *
	 * @param value   boolean value that indicates wither the output is high(true) or low(false)
	 * @param channel
	 */
	private void onOutputChange(boolean value, int channel) {
		if (value) {
			outputs[channel].changeColor(ConnectionPoint.ConnectorColor.DEFAULT_HIGH);
		} else {
			outputs[channel].changeColor(ConnectionPoint.ConnectorColor.DEFAULT_LOW);
		}
	}


	/**
	 * Gets the model Component this Controller is based on.
	 *
	 * @return The model Component this Controller is based on.
	 */
	public Component getModel() {
		return model;
	}

	String getID() {
		return model.getId();
	}

	/**
	 * Used to keep the models coordinates updated with the changes from the UI.
	 */
	private void onCoordinateChange() {
		model.getPosition().setX(getCoordinateX());
		model.getPosition().setY(getCoordinateY());
	}


	ConnectionPointController getInputConnectionPoint(int i) {
		return inputs[i];
	}

	ConnectionPointController getOutputConnectionPoint(int i) {
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

	private void onClick(){
		Input i = (Input) model;
		i.switchState();
	}
}