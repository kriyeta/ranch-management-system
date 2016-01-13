package com.techlify.ranchmanager.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import com.techlify.ranchmanager.util.PrintLog;

/**
 * @author kamal
 *
 */
public class ImportDataController {

	@FXML
	private AnchorPane viewBox;

	@FXML
	private VBox filterBox;

	@FXML
	private Button browseButton;
	
	@FXML
	private Label errorLabel;

	@FXML
	private TextField importDataFilePath;

	File file = null;

	@FXML
	private void initialize() {
		importDataFilePath.setDisable(false);
	}

	@FXML
	void browsePhoto(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilterH2Db = new FileChooser.ExtensionFilter(
				"JPG files (*.jpg)", "*.h2.db");

		fileChooser.getExtensionFilters().addAll(extFilterH2Db);

		// Show open file dialog
		file = fileChooser.showOpenDialog(null);
		String filePath = file.getAbsolutePath();
		PrintLog.printLog(filePath);
		importDataFilePath.setText(filePath);
		importDataFilePath.positionCaret(filePath.length());
	}

	@FXML
	void importData(ActionEvent event){
		String path = null;
		try {
			path = new File(".").getCanonicalPath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Files.copy(file.toPath(), new File(path
					+ "/ranch-manager-data/h2db.h2.db").toPath(),
					StandardCopyOption.REPLACE_EXISTING);
			errorLabel.setWrapText(true);
			errorLabel.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
			errorLabel.setText("Database imported successfully. Please restart application.");
		} catch (Exception e) {
			errorLabel.setWrapText(true);
			errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
			errorLabel.setText("Ooops something went wrong, please try again with correct file.");
			e.printStackTrace();
		}
	}

}
