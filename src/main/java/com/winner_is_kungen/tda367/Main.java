package com.winner_is_kungen.tda367;

import com.winner_is_kungen.tda367.controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
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
	}
}