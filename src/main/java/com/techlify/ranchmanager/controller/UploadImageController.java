package com.techlify.ranchmanager.controller;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import com.techlify.ranchmanager.util.PrintLog;

/**
 * @author kamal
 *
 */
public class UploadImageController {

	@FXML
	private TextField imagePath;

	@FXML
	private Button browseButton;

	@FXML
	void browsePhoto(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter(
				"JPG files (*.jpg)", "*.JPG");
		FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(
				"PNG files (*.png)", "*.PNG");
		fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

		// Show open file dialog
		File file = fileChooser.showOpenDialog(null);
		String filePath	=	file.getAbsolutePath();
		PrintLog.printLog(filePath);
		imagePath.setText(filePath);
		imagePath.positionCaret(filePath.length());
	}

}
