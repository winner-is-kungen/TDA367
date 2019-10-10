package com.winner_is_kungen.tda367.view.canvas;

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
	private final Paint high_connector = new Color(1,0,0,1);
	private final double connectionRadius = 8.0;


	public ConnectionPoint() {
		super(0, 0, 0);
		this.radiusProperty().set(connectionRadius);
		this.fillProperty().set(default_connector);
	}

	public enum ConnectorColor {
		DEFAULT, ACTIVE, DISABLED, HIGH
	}

	public void changeColor(ConnectorColor color) {
		switch (color) {
			case DEFAULT:
				this.fillProperty().set(default_connector);
				break;
			case HIGH:
				this.fillProperty().set(high_connector);
				break;
			case ACTIVE:
				this.fillProperty().set(active_connector);
				break;
			case DISABLED:
				this.fillProperty().set(disabled_connector);
				break;
		}
	}
}
