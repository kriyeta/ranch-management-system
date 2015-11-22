package com.techlify.ranchmanager.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.hibernate.Session;

import com.techlify.ranchmanager.common.AllPaths;
import com.techlify.ranchmanager.convertor.AnimalTypeConvertor;
import com.techlify.ranchmanager.dao.AnimalType;
import com.techlify.ranchmanager.util.FXMLUtility;
import com.techlify.ranchmanager.util.HibernateUtil;
import com.techlify.ranchmanager.util.PrintLog;

/**
 * @author kamal
 *
 */
public class AddAnimalController {

	@FXML
	private VBox typeVBox;

	@FXML
	private HBox typeHBox;

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
	private ComboBox<AnimalType> type;

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

	static boolean isFilled = false;

	@FXML
	private void initialize() {
		type.setConverter(new AnimalTypeConvertor());
	}

	@FXML
	void addNewType(ActionEvent event) {
		isFilled = false;
		typeHBox.setVisible(false);
		AddNewAnimalTypeController.setTypeHBox(typeHBox);
		GridPane loadFxmlOnComponent = (GridPane) FXMLUtility
				.loadFxmlOnComponent(AllPaths.ADD_NEW_TYPE_ANIMAL_PAGE);
		loadFxmlOnComponent.setMaxSize(400, 400);
		typeVBox.getChildren().add(0, loadFxmlOnComponent);
	}

	@FXML
	void browsePhoto(ActionEvent event) {

	}

	@FXML
	void addMorePhotos(ActionEvent event) {

	}

	@FXML
	void fillAnimalTypes(MouseEvent event) {

		if (!isFilled) {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			List<AnimalType> resultList = session.createCriteria(
					AnimalType.class).list();
			PrintLog.printLog("num of animal types:" + resultList.size());
			ArrayList<AnimalType> allTypes = new ArrayList<AnimalType>();
			for (AnimalType next : resultList) {
				allTypes.add(next);
			}
			type.setItems(FXCollections.observableArrayList(allTypes));
			isFilled = true;
		}
		PrintLog.printLog("mouse clicked");
	}

	@FXML
	void submitForm(ActionEvent event) {

	}

	@FXML
	void clearForm(ActionEvent event) {

	}

}
