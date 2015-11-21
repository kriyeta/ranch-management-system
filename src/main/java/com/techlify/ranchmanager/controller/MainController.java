package com.techlify.ranchmanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import com.techlify.ranchmanager.common.AllPaths;
import com.techlify.ranchmanager.util.FXMLUtility;

/**
 * @author kamal
 *
 */
public class MainController {
	
	@FXML
    private Accordion mainMenu;
	
	@FXML
    private StackPane detailedPane;
	
	@FXML
	private Button addAnimal;

    @FXML
    private Button updateAnimal;

    @FXML
    private Button viewAnimals;
    
    @FXML
	private void initialize() {
    	mainMenu.setExpandedPane(mainMenu.getPanes().get(0));
	}

    @FXML
    public void addAnimal(ActionEvent event) {
    	FXMLUtility.loadFxmlOnStackPane(AllPaths.ADD_ANIMAL_PAGE, detailedPane );
    }
    
    @FXML
    public void updateAnimal(ActionEvent event) {
    	
    }
    
    @FXML
    public void viewAnimals(ActionEvent event) {
    	
    }
}
