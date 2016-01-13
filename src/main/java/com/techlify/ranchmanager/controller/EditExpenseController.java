package com.techlify.ranchmanager.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

import com.techlify.ranchmanager.common.AllControllers;
import com.techlify.ranchmanager.common.Messages;
import com.techlify.ranchmanager.dao.Expense;
import com.techlify.ranchmanager.util.DateUtils;
import com.techlify.ranchmanager.util.FormUtil;
import com.techlify.ranchmanager.util.HibernateUtil;
import com.techlify.ranchmanager.util.PrintLog;

/**
 * @author kamal
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EditExpenseController implements Initializable {

	@FXML
	private TextField amount;

	@FXML
	private Button submitButton;

	@FXML
	private HBox typeHBox;

	@FXML
	private TextField id;
	
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
	static Expense currentExpense	=	null;

	public void initialize(URL location, ResourceBundle resources) {
		isFilled	=	false;
		id.setText(currentExpense.getId().toString());
		id.setDisable(true);
		amount.setText(currentExpense.getAmount().toString());
		details.setText(currentExpense.getDetails());
		dateAdded.setValue(DateUtils.asLocalDate(currentExpense.getDate()));
		type.getSelectionModel().select(currentExpense.getType());
		
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
				expense.setId(Long.parseLong(id.getText()));
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
						.updateObjectToDatabase(expense);
				if (addObjectToDatabase == true) {
					errorLabel.setText("Expense is updated successfully");
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
			errorLabel.setWrapText(true);
			errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
			errorLabel.setText("Ooops something went wrong, please try again.");
		} finally {
			ViewExpensesController.data = ViewExpensesController
					.getInitialTableData();
			AllControllers.viewExpensesController.expensesTable
					.setItems(ViewExpensesController.data);
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
			errorLabel.setText(Messages.VALIDATION_FAILURE_MESSAGE);
		}
		return isValid;
	}
}
