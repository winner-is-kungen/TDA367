package com.winner_is_kungen.tda367;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
			primaryStage.setTitle("Hello World!");
			primaryStage.setScene(new Scene(root, 300, 275));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			primaryStage.close();
		}
	}
}