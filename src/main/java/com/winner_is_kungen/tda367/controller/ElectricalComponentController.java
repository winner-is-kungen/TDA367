package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.view.canvas.InfiniteCanvas;
import com.winner_is_kungen.tda367.view.canvas.InfiniteCanvasBlock;

public abstract class ElectricalComponentController extends InfiniteCanvasBlock {
	/*private final ElectricalComponent model;*/

	public ElectricalComponentController(/*ElectricalComponent model*/) {
		/*this.model = model;*/

		/* TODO: Update the view from the model.
		setCoordinateX(model.?);
		setCoordinateY(model.?);
		setSizeX(model.?);
		setSizeY(model.?);
		*/

		InfiniteCanvas.addCoordinateListener(this, this::onCoordinateChange);
		InfiniteCanvas.addSizeListener(this, this::onSizeChange);
	}

	/**
	 * Used to keep the models coordinated updated with the changes from the UI.
	 */
	private void onCoordinateChange() {
		/* TODO: Update the model with the new coordinates.
		model.? = getCoordinateX();
		model.? = getCoordinateY();
		*/
	}
	/**
	 * Used to keep the models size updated with the changes from the UI.
	 */
	private void onSizeChange() {
		/* TODO: Update the model with the new size.
		model.? = getSizeX();
		model.? = getSizeY();
		*/
	}

	// TODO: Probably some more generic interaction with the model.
}