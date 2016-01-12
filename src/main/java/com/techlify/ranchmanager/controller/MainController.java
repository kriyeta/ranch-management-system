package com.techlify.ranchmanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import com.techlify.ranchmanager.common.AllControllers;
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
	private Label title;
	
	//animal related controls
	@FXML
	private Button addAnimal;

    @FXML
    private Button viewAnimals;
    
	//expenses related controls
    @FXML
    private Button addExpenses;
    
    @FXML
    private Button viewExpenses;
  
    //Animals Mating related controls
    @FXML
    private Button addAnimalsMating;
    
    @FXML
    private Button viewAnimalsMating;
    
    
    @FXML
	private void initialize() {
    	mainMenu.setExpandedPane(mainMenu.getPanes().get(0));
    	viewAnimals(null);
	}

    @FXML
    public void addAnimal(ActionEvent event) {
    	title.setText("Add Animal");
    	FXMLUtility.loadFxmlOnStackPane(AllPaths.ADD_ANIMAL_PAGE, detailedPane );
    }
    
    @FXML
    public void updateAnimal(ActionEvent event) {
    	
    }
    
    @FXML
    public void viewAnimals(ActionEvent event) {
    	title.setText("All Animals");
    	AllControllers.viewAnimalsController = (ViewAnimalsController)FXMLUtility.loadFxmlOnStackPane(AllPaths.VIEW_ANIMAL_PAGE, detailedPane );
    }
    
    @FXML
    void addExpenses(ActionEvent event) {
    	title.setText("Add Expense");
    	FXMLUtility.loadFxmlOnStackPane(AllPaths.ADD_EPENSES_PAGE, detailedPane );
    }

    @FXML
    void viewExpenses(ActionEvent event) {
    	title.setText("All Expenses");
    	AllControllers.viewExpensesController	=	(ViewExpensesController) FXMLUtility.loadFxmlOnStackPane(AllPaths.VIEW_EXPENSES_PAGE, detailedPane );
    }
    
    @FXML
    void addAnimalsMating(ActionEvent event) {
    	title.setText("Add Animals Mating");
    	FXMLUtility.loadFxmlOnStackPane(AllPaths.ADD_ANIMALS_MATING_PAGE, detailedPane );
    }

    @FXML
    void viewAnimalsMating(ActionEvent event) {
    	title.setText("All Animals Mating");
    	FXMLUtility.loadFxmlOnStackPane(AllPaths.VIEW_ANIMALS_MATING_PAGE, detailedPane );
    }
    
    @FXML
    void exportData(ActionEvent event) {
    	title.setText("EXPORT DATA");
    	FXMLUtility.loadFxmlOnStackPane(AllPaths.EXPORT_DATA_PAGE, detailedPane );
    }
    
    @FXML
    void importData(ActionEvent event) {
    	title.setText("IMPORT DATA");
    	FXMLUtility.loadFxmlOnStackPane(AllPaths.IMPORT_DATA_PAGE, detailedPane );
    }
}
