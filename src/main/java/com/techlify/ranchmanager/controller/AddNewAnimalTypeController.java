package com.techlify.ranchmanager.controller;

import java.io.Serializable;

import org.hibernate.Session;

import com.techlify.ranchmanager.dao.AnimalType;
import com.techlify.ranchmanager.util.HibernateUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author kamal
 *
 */
public class AddNewAnimalTypeController {
	
	@FXML
    private Button addNewType;

    @FXML
    private TextField name;

    @FXML
    private TextField description;

    @FXML
    private GridPane addNewAnimalType;

    @FXML
    private Label errorLabel;
    
    private static HBox typeHBox;

    @FXML
    void addNewType(ActionEvent event) {
    	
    	VBox typeVBox	=	(VBox) typeHBox.getParent();
    	typeVBox.getChildren().remove(addNewAnimalType);
    	
    	//add type
    	 Session session = HibernateUtil.getSessionFactory().openSession(); 
         session.beginTransaction();
         AnimalType animalType	=	new AnimalType();
         animalType.setName(name.getText().trim());
         animalType.setDescription(description.getText());
         Serializable save = session.save(animalType);
         session.getTransaction().commit();
         if(save!=null){
        	 typeHBox.setVisible(true);
         }
    }

    @FXML
    void cancelAddingNewType(ActionEvent event) {
    	VBox typeVBox	=	(VBox) typeHBox.getParent();
    	typeVBox.getChildren().remove(addNewAnimalType);
       	 typeHBox.setVisible(true);
    }

    static void setTypeHBox(HBox hBox){
    	typeHBox	=	hBox;
    }

}
