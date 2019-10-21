package com.winner_is_kungen.tda367.controller.LogicGates;

import com.winner_is_kungen.tda367.controller.ComponentController;
import com.winner_is_kungen.tda367.model.LogicGates.Output;
import com.winner_is_kungen.tda367.model.util.EventBusEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OutputController extends ComponentController {
	@FXML
	public Label componentSymbol;

	private Output model;

	public OutputController(Output model) {
		super(model, null);

		this.model = model;
		model.getEventBus().addListener(Output.changeEvent, this::onModelChange);
		onModelChange(null);
	}

	private void onModelChange(EventBusEvent ignored) {
		if (model.getInputValue()) {
			componentSymbol.setText("👍");
		}
		else {
			componentSymbol.setText("👎");
		}
	}
}