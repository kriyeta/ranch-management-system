package com.techlify.ranchmanager.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.techlify.ranchmanager.common.Messages;
import com.techlify.ranchmanager.convertor.AnimalConvertor;
import com.techlify.ranchmanager.convertor.AnimalTypeConvertor;
import com.techlify.ranchmanager.dao.Animal;
import com.techlify.ranchmanager.dao.AnimalType;
import com.techlify.ranchmanager.dao.AnimalsMating;
import com.techlify.ranchmanager.util.DateUtils;
import com.techlify.ranchmanager.util.HibernateUtil;
import com.techlify.ranchmanager.util.PrintLog;

/**
 * @author kamal
 *
 */
public class AddAnimalsMatingController {

	@FXML
	private Button submitButton;

	@FXML
	private DatePicker endDate;

	@FXML
	private Label errorLabel;

	@FXML
	private Button resetButton;

	@FXML
	private DatePicker startDate;

	@FXML
	private GridPane createUserForm;

	@FXML
	private ComboBox<AnimalType> maleParentType;

	@FXML
	private ComboBox<Animal> maleParent;

	@FXML
	private ComboBox<AnimalType> femaleParentType;

	@FXML
	private ComboBox<Animal> femaleParent;

	private static boolean isMaleParentTypeFilled = false;
	private static boolean isFemaleParentTypeFilled = false;

	@FXML
	private void initialize() {
		isMaleParentTypeFilled = false;
		isFemaleParentTypeFilled = false;

		// adding convertor for type combobox
		maleParent.setConverter(new AnimalConvertor());

		// adding convertor for type combobox
		maleParentType.setConverter(new AnimalTypeConvertor());

		maleParentType.setOnAction((event) -> {
			AnimalType selectedAnimalType = maleParentType.getSelectionModel()
					.getSelectedItem();
			PrintLog.printLog("ComboBox Action (selected: "
					+ selectedAnimalType.toString() + ")");
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			List<Animal> resultList = session.createCriteria(Animal.class)
					.add(Restrictions.eq("typeId", selectedAnimalType))
					.add(Restrictions.eq("gender", "Male")).list();
			PrintLog.printLog("num of animal types:" + resultList.size());
			ArrayList<Animal> selectedTypeAnimals = new ArrayList<Animal>();
			for (Animal next : resultList) {
				selectedTypeAnimals.add(next);
			}
			maleParent.setItems(FXCollections
					.observableArrayList(selectedTypeAnimals));
		});

		// adding convertor for type combobox
		femaleParent.setConverter(new AnimalConvertor());

		// adding convertor for type combobox
		femaleParentType.setConverter(new AnimalTypeConvertor());

		femaleParentType.setOnAction((event) -> {
			AnimalType selectedAnimalType = femaleParentType
					.getSelectionModel().getSelectedItem();
			PrintLog.printLog("ComboBox Action (selected: "
					+ selectedAnimalType.toString() + ")");
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			List<Animal> resultList = session.createCriteria(Animal.class)
					.add(Restrictions.eq("typeId", selectedAnimalType))
					.add(Restrictions.eq("gender", "Female")).list();
			PrintLog.printLog("num of animal types:" + resultList.size());
			ArrayList<Animal> selectedTypeAnimals = new ArrayList<Animal>();
			for (Animal next : resultList) {
				selectedTypeAnimals.add(next);
			}
			femaleParent.setItems(FXCollections
					.observableArrayList(selectedTypeAnimals));
		});

	}

	@FXML
	void fillFemaleParentTypes(MouseEvent event) {
		if (!isFemaleParentTypeFilled) {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			List<AnimalType> resultList = session.createCriteria(
					AnimalType.class).list();
			PrintLog.printLog("num of animal types:" + resultList.size());
			ArrayList<AnimalType> allTypes = new ArrayList<AnimalType>();
			for (AnimalType next : resultList) {
				allTypes.add(next);
			}
			femaleParentType.setItems(FXCollections
					.observableArrayList(allTypes));
			isFemaleParentTypeFilled = true;
		}
	}

	@FXML
	void fillMaleParentTypes(MouseEvent event) {
		if (!isMaleParentTypeFilled) {
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			List<AnimalType> resultList = session.createCriteria(
					AnimalType.class).list();
			PrintLog.printLog("num of animal types:" + resultList.size());
			ArrayList<AnimalType> allTypes = new ArrayList<AnimalType>();
			for (AnimalType next : resultList) {
				allTypes.add(next);
			}
			maleParentType
					.setItems(FXCollections.observableArrayList(allTypes));
			isMaleParentTypeFilled = true;
		}
	}

	@FXML
	void submitForm(ActionEvent event) {

		boolean isValid = checkForValidations();

		try {
			if (isValid) {
				AnimalsMating animalsMating = new AnimalsMating();
				Date startDateValue = DateUtils.asDate(startDate.getValue());
				animalsMating.setStartDate(startDateValue);
				Date endDateValue = DateUtils.asDate(endDate.getValue());
				animalsMating.setEndDate(endDateValue);
				animalsMating.setMaleParent(maleParent.getSelectionModel()
						.getSelectedItem());
				animalsMating.setFemaleParent(femaleParent.getSelectionModel()
						.getSelectedItem());

				boolean addObjectToDatabase = HibernateUtil
						.addObjectToDatabase(animalsMating);
				if (addObjectToDatabase == true) {
					errorLabel.setText("Animals Mating Record is added successfully");
					clearForm(null);
				} else {
					errorLabel.setWrapText(true);
					errorLabel
							.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
					errorLabel
							.setText(Messages.FORM_SUBMIT_FAILURE_MESSAGE);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			errorLabel.setWrapText(true);
			errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
			errorLabel.setText("Ooops something went wrong, please try again.");
		}
	}

	@FXML
	public void clearForm(ActionEvent event) {
		PrintLog.printLog("Clearing form");
		for (Node node : createUserForm.getChildren()) {
			if (node instanceof VBox) {
				PrintLog.printLog(node);
				for (Node node1 : ((VBox) node).getChildren()) {
					PrintLog.printLog(node1);
					if (node1 instanceof TextField) {
						PrintLog.printLog(node1);
						((TextField) node1).clear();
					}
				}
			}
		}
	}
	
	/*
	 * Other methods
	 */
	private Boolean checkForValidations() {
		boolean isValid = true;

		// if(!password.getText().equals(reenterPassword.getText())){
		// isValid = false;
		// VBox parent = (VBox)password.getParent();
		// Label label = new Label("Rentered password doesn't match.");
		// label.setId("errorMessages");
		// parent.getChildren().add(label);
		// }

		if (!isValid) {
			errorLabel.setText(Messages.VALIDATION_FAILURE_MESSAGE);
		}
		return isValid;
	}

}
