package io.vonish.autocomplete.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class SampleController {
	
	private static final List<String> OPTIONS = List.of(
			"Wired Mouse",
			"Wireless Mouse",
			"Keyboard RGB",
			"Mechanical Keyboard",
			"Speaker",
			"Bluetooth Speaker",
			"Wired Earphone",
			"Wired In-ear-monitor",
			"Wired Headphone",
			"Wired Headset",
			"Wireless Earphone",
			"Wireless In-ear-monitor",
			"Wireless Headphone",
			"Wireless Headset"
	);
	
	@FXML private TextField textField;
	@FXML private VBox resultVBox;
	@FXML private ScrollPane resultPane;
	
	private final PauseTransition pause;
	
	public SampleController() {
		this.pause = new PauseTransition(Duration.millis(500));
	}
	
	@FXML
	public void initialize() {
		setResultVisibility(false);
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			pause.setOnFinished(e -> populateOptions(newValue));
			pause.playFromStart();
		});
	}
	
	private void populateOptions(String searchString) {
		if (searchString == null || searchString.trim().isEmpty()) {
			resultVBox.getChildren().clear();
			setResultVisibility(false);
			return;
		}
		var options = OPTIONS.stream()
				.filter(opt -> opt.toLowerCase().contains(searchString.toLowerCase()))
				.map(opt -> getLabel(opt))
				.collect(Collectors.toList());
		resultVBox.getChildren().setAll(options);
		if (options.isEmpty()) {
			setResultVisibility(false);
			return;
		}
		setResultVisibility(true);
	}
	
	private Label getLabel(String value) {
		try {
			final Label label = FXMLLoader.load(getClass().getResource("/fxml/Label.fxml"));
			label.setText(value);
			return label;
		} catch (IOException e) {
			return new Label(value);
		}
	}
	
	private void setResultVisibility(boolean visible) {
		resultVBox.setVisible(visible);
		resultPane.setVisible(visible);
	}

}
