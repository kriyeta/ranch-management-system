package com.techlify.ranchmanager.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import org.hibernate.criterion.Projections;

import com.techlify.ranchmanager.dao.Expense;
import com.techlify.ranchmanager.util.DateUtils;
import com.techlify.ranchmanager.util.FormUtil;
import com.techlify.ranchmanager.util.HibernateUtil;
import com.techlify.ranchmanager.util.PrintLog;

/**
 * @author kamal
 *
 */
public class AddExpenseController {

	@FXML
	private TextField amount;

	@FXML
	private Button submitButton;

	@FXML
	private HBox typeHBox;

	@FXML
	private TextField details;

	@FXML
	private TextField newType;

	@FXML
	private GridPane formGrid;

	@FXML
	private ComboBox<String> type;

	@FXML
	private Label errorLabel;

	@FXML
	private DatePicker dateAdded;

	@FXML
	private Button resetButton;

	@FXML
	private VBox typeVBox;

	static boolean isFilled = false;

	@FXML
	private void initialize() {
		isFilled	=	false;
	}

	@FXML
	void fillExpenseTypes(MouseEvent event) {
		PrintLog.printLog("mouse clicked");
		if (!isFilled) {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			List<String> resultList = session
					.createCriteria(Expense.class)
					.setProjection(
							Projections.distinct(Projections.property("type")))
					.list();
			PrintLog.printLog("num of expenses types:" + resultList.size());
			ArrayList<String> allTypes = new ArrayList<String>();
			for (String next : resultList) {
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
				
				Expense expense	=	new Expense();
				expense.setAmount(Long.parseLong(amount.getText()));
				Date value = DateUtils.asDate(dateAdded.getValue());
				expense.setDate(value);
				expense.setDetails(details.getText());
				String newTypeValue	=	"Default";
				if(newType.getText()!=null && !newType.getText().equals("")){
					newTypeValue	=	newType.getText();
				} else if(type.getSelectionModel().getSelectedItem() != null) {
					newTypeValue	=	type.getSelectionModel().getSelectedItem();
				}
				expense.setType(newTypeValue);

				boolean addObjectToDatabase = HibernateUtil
						.addObjectToDatabase(expense);
				if (addObjectToDatabase == true) {
					errorLabel.setText("Expense is added successfully");
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
	void clearForm(ActionEvent event) {
		PrintLog.printLog("Clearing form");
		FormUtil.clearNode(formGrid);
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
