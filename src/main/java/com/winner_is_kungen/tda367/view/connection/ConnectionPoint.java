package com.winner_is_kungen.tda367.view.connection;

import javafx.scene.paint.*;
import javafx.scene.shape.Circle;

/**
 * Visual representation of the connection points on a circle
 */

public class ConnectionPoint extends Circle {

	// Config for the visual representation of the connection point
	private final Paint default_connector = new RadialGradient(0.0, 0.0, 0.0, 0.0, 20.0, false, CycleMethod.NO_CYCLE, new Stop(0, Color.web("rgba(184,184,184,1)")), new Stop(1, Color.web("#49ff00")));
	private final Paint active_connector = new Color(0, 1, 0, 1);
	private final Paint disabled_connector = new Color(0, 0, 0, 1);
	private final Paint high_connector = new Color(1, 0, 0, 1);
	private Paint temporarySavedColor;
	private final double connectionRadius = 8.0;


	protected ConnectionPoint() {
		super(0, 0, 0);
		this.radiusProperty().set(connectionRadius);
		this.fillProperty().set(default_connector);
	}

	public void changeColor(ConnectorColor color) {
		switch (color) {
			case DEFAULT_LOW:
				this.fillProperty().set(default_connector);
				break;
			case DEFAULT_HIGH:
				this.fillProperty().set(high_connector);
				break;
			case ACTIVE_HIGH:
				this.fillProperty().set(active_connector);
				break;
			case ACTIVE_LOW:
				this.fillProperty().set(active_connector);
				break;
			case DISABLED:
				this.fillProperty().set(disabled_connector);
				break;
		}
	}

	/**
	 *  Called from onClick in ConnectionPointController to save the current color before changing it so that it still
	 *  indicates if its high or low
	 *
	 */
	protected void saveColor() {
		temporarySavedColor = this.fillProperty().getValue();
	}

	public Paint getTemporarySavedColor() {
		return temporarySavedColor;
	}

	public enum ConnectorColor {
		DEFAULT_LOW, DEFAULT_HIGH, ACTIVE_HIGH, ACTIVE_LOW, DISABLED
	}
}
