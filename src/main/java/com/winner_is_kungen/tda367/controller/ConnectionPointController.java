package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.view.canvas.ConnectionPoint;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ConnectionPointController extends ConnectionPoint {
	int channel;
	boolean isInput;

	ConnectionPointController(int channel, boolean isInput){
		super();
		this.channel = channel;
		this.isInput = isInput;
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent){
				mouseEvent.consume();
				changeColor(ConnectorColor.ACTIVE);
			}
		});
	}
}
