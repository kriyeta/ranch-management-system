package com.techlify.ranchmanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * @author kamal
 *
 */
public class AddAnimalController {
	
	@FXML
    private Button addNewType;

    @FXML
    private Button submitButton;

    @FXML
    private ChoiceBox<?> gender;

    @FXML
    private TextField imagePath;

    @FXML
    private TextField numbers;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private ChoiceBox<?> type;

    @FXML
    private Button browseButton;

    @FXML
    private Button addMorePhotos;

    @FXML
    private VBox photosGroup;

    @FXML
    private Label errorLabel;

    @FXML
    private Button resetButton;

    @FXML
    private GridPane createUserForm;
    
    @FXML
	private void initialize() {

    }

    @FXML
    void addNewType(ActionEvent event) {

    }

    @FXML
    void browsePhoto(ActionEvent event) {

    }

    @FXML
    void addMorePhotos(ActionEvent event) {

    }

    @FXML
    void submitForm(ActionEvent event) {

    }

    @FXML
    void clearForm(ActionEvent event) {

    }


}
