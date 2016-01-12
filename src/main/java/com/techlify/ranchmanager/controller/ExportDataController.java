package com.techlify.ranchmanager.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import com.techlify.ranchmanager.util.PrintLog;

/**
 * @author kamal
 *
 */
public class ExportDataController {

	@FXML
	private AnchorPane viewBox;

	@FXML
	private VBox filterBox;

	@FXML
	private Label errorLabel;

	@FXML
	void exportData(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilterH2Db = new FileChooser.ExtensionFilter(
				"JPG files (*.jpg)", "*.db");

		fileChooser.getExtensionFilters().addAll(extFilterH2Db);

		// Show open file dialog
		File file = fileChooser.showSaveDialog(null);
		String filePath = file.getAbsolutePath();
		PrintLog.printLog(filePath);

		String path = null;
		try {
			path = new File(".").getCanonicalPath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Files.copy(
					new File(path + "/ranch-manager-data/h2db.h2.db").toPath(),
					file.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
			errorLabel.setWrapText(true);
			errorLabel.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
			errorLabel
					.setText("Database exported successfully. Keep file backup to use in future.");
		} catch (Exception e) {
			errorLabel.setWrapText(true);
			errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
			errorLabel
					.setText("Ooops something went wrong, please try again with correct location.");
			e.printStackTrace();
		}
	}

}
