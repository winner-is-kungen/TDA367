module TDA367 {
	requires javafx.controls;
	requires javafx.fxml;

	opens com.winner_is_kungen.tda367 to javafx.fxml;
	exports com.winner_is_kungen.tda367;
}