package com.winner_is_kungen.tda367.view.canvas;

import javafx.beans.DefaultProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

@DefaultProperty("content")
public class InfiniteCanvasBlock extends AnchorPane {
	private final ObjectProperty<Node> content = new SimpleObjectProperty<Node>(this, "content");

	public InfiniteCanvasBlock() {
		super();
	}
	public InfiniteCanvasBlock(Node content) {
		this();
		setContent(content);
	}

	public final Node getContent() {
		return content.get();
	}
	public final void setContent(Node value) {
		Node previous = getContent();
		if (previous != null) {
			getChildren().remove(previous);
		}

		contentProperty().set(value);

		if (value != null) {
			getChildren().add(value);

			AnchorPane.setLeftAnchor(value, 0.0d);
			AnchorPane.setBottomAnchor(value, 0.0d);
			AnchorPane.setRightAnchor(value, 0.0d);
			AnchorPane.setTopAnchor(value, 0.0d);
		}
	}
	public final ObjectProperty<Node> contentProperty() {
		return content;
	}
}