package com.techlify.ranchmanager.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.techlify.ranchmanager.common.AllPaths;
import com.techlify.ranchmanager.convertor.AnimalConvertor;
import com.techlify.ranchmanager.convertor.AnimalTypeConvertor;
import com.techlify.ranchmanager.dao.Animal;
import com.techlify.ranchmanager.dao.AnimalType;
import com.techlify.ranchmanager.dao.Photo;
import com.techlify.ranchmanager.util.DateUtils;
import com.techlify.ranchmanager.util.FXMLUtility;
import com.techlify.ranchmanager.util.FormUtil;
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
	private ComboBox<String> gender;

	@FXML
	private TextField numbers;

	@FXML
	private DatePicker dateOfBirth;

	@FXML
	private ComboBox<AnimalType> type;

	@FXML
	private ComboBox<Animal> maleParent;

	@FXML
	private ComboBox<Animal> femaleParent;

	@FXML
	private Label maxLimitReachedLabel;

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
	static boolean isMaleParentTypeFilled = false;
	static boolean isFemaleParentTypeFilled = false;

	@FXML
	private void initialize() {
		isFilled = false;
		isMaleParentTypeFilled = false;
		isFemaleParentTypeFilled = false;
		// adding convertor for type combobox
		type.setConverter(new AnimalTypeConvertor());

		// adding convertor for type combobox
		maleParent.setConverter(new AnimalConvertor());

		// adding convertor for type combobox
		femaleParent.setConverter(new AnimalConvertor());

		type.setOnAction((event) -> {
			
			AnimalType selectedAnimalType = type.getSelectionModel()
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
			
			PrintLog.printLog("ComboBox Action (selected: "
					+ selectedAnimalType.toString() + ")");
			session.getTransaction().commit();
			
			session.beginTransaction();
			List<Animal> resultList1 = session.createCriteria(Animal.class)
					.add(Restrictions.eq("typeId", selectedAnimalType))
					.add(Restrictions.eq("gender", "Female")).list();
			PrintLog.printLog("num of animal types:" + resultList1.size());
			ArrayList<Animal> selectedTypeAnimals1 = new ArrayList<Animal>();
			for (Animal next : resultList1) {
				selectedTypeAnimals1.add(next);
			}
			femaleParent.setItems(FXCollections
					.observableArrayList(selectedTypeAnimals1));
			session.getTransaction().commit();
		});

		// adding one browse button initially
		addMorePhotos(null);

		// setting genders
		ArrayList<String> allGenders = new ArrayList<String>();
		allGenders.add("Male");
		allGenders.add("Female");
		gender.setItems(FXCollections.observableArrayList(allGenders));
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
	void addMorePhotos(ActionEvent event) {
		HBox uploadImageHBox = (HBox) FXMLUtility
				.loadFxmlOnComponent(AllPaths.UPLOAD_IMAGE_PAGE);
		if (photosGroup.getChildren().size() < 7) {
			photosGroup.getChildren().add(photosGroup.getChildren().size() - 1,
					uploadImageHBox);
		} else {
			addMorePhotos.setText("Reached maximum limit of images");
		}
	}

	@FXML
	void fillAnimalTypes(MouseEvent event) {

		if (!isFilled) {
			Session session = HibernateUtil.getSession();
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
		boolean isValid = checkForValidations();

		try {
			if (isValid) {
				Animal animal = new Animal();
				animal.setNumbers(numbers.getText());
				animal.setTypeId(type.getSelectionModel().getSelectedItem());
				animal.setGender(gender.getSelectionModel().getSelectedItem());
				Date value = DateUtils.asDate(dateOfBirth.getValue());
				animal.setDateOfBirth(value);
				animal.setMaleParent(maleParent.getSelectionModel()
						.getSelectedItem());
				animal.setFemaleParent(femaleParent.getSelectionModel()
						.getSelectedItem());

				ObservableList<Node> children = photosGroup.getChildren();
				LinkedList<Photo> allPhotos = new LinkedList<Photo>();
				for (Node node1 : children) {
					if (node1 instanceof HBox) {
						ObservableList<Node> photoChildren = ((HBox) node1)
								.getChildren();
						for (Node node : photoChildren) {
							if (node instanceof TextField) {
								String filePath = ((TextField) node).getText();
								PrintLog.printLog(((TextField) node).getText());
								if (filePath != null && !filePath.isEmpty()) {
									Photo photo = new Photo();
									File file = new File(filePath);
									byte[] bFile = new byte[(int) file.length()];
									try {
										FileInputStream fileInputStream = new FileInputStream(
												file);
										fileInputStream.read(bFile);
										fileInputStream.close();
									} catch (Exception e) {
										e.printStackTrace();
									}
									photo.setPhoto(bFile);
									allPhotos.add(photo);
								}

							}
						}
					}
				}

				animal.setPhotos(allPhotos);

				boolean addObjectToDatabase = HibernateUtil
						.addObjectToDatabase(animal);
				if (addObjectToDatabase == true) {
					errorLabel.setText("Animal is added successfully");
					clearForm(null);
				} else {
					errorLabel.setWrapText(true);
					errorLabel
							.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
					errorLabel
							.setText("Ooops something went wrong, please try again.");
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
		FormUtil.clearNode(createUserForm);
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
			errorLabel.setText("Error in form.");
		}
		return isValid;
	}

}
