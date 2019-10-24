package com.winner_is_kungen.tda367.controller.LogicGates;

import com.winner_is_kungen.tda367.controller.ComponentController;
import com.winner_is_kungen.tda367.model.LogicGates.Output;
import com.winner_is_kungen.tda367.model.util.EventBusEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class OutputController extends ComponentController {
	private static final Paint lowBackground =  new Color(0, 0, 0, 1);
	private static final Paint highBackground = new Color(1, 0, 0, 1);

	@FXML
	public Label componentSymbol;

	private final Output model;

	public OutputController(Output model) {
		super(model, "█");

		this.model = model;
		model.getEventBus().addListener(Output.changeEvent, this::onModelChange);
		onModelChange(null);
	}

	private void onModelChange(EventBusEvent ignored) {
		if (model.getInputValue()) {
			componentSymbol.setTextFill(highBackground);
		}
		else {
			componentSymbol.setTextFill(lowBackground);
		}
	}
}