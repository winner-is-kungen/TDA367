package com.winner_is_kungen.tda367;

import com.winner_is_kungen.tda367.controller.MainController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		MainController root = new MainController();
		primaryStage.setScene(new Scene(root, 800, 600));
		primaryStage.show();

		primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			final KeyCombination keyComb = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);

			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyComb.match(keyEvent)){
					root.saveFile();
					keyEvent.consume();
				}
			}
		});
	}
}