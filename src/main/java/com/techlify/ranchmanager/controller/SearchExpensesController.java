package com.techlify.ranchmanager.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.techlify.ranchmanager.common.AllControllers;
import com.techlify.ranchmanager.dao.AnimalsMating;
import com.techlify.ranchmanager.dao.Expense;
import com.techlify.ranchmanager.util.DateUtils;
import com.techlify.ranchmanager.util.FormUtil;
import com.techlify.ranchmanager.util.HibernateUtil;
import com.techlify.ranchmanager.util.PrintLog;

/**
 * @author kamal
 *
 */
public class SearchExpensesController {

	@FXML
	private TextField amount;

	@FXML
	private ComboBox<String> expenseType;

	@FXML
	private Button filterExpenses;

	@FXML
	private AnchorPane searchPane;

	@FXML
	private VBox filterBox;

	@FXML
	private DatePicker endDate;

	@FXML
	private Button clearFilter;

	@FXML
	private DatePicker startDate;

	static boolean isFilled = false;

	@FXML
	private void initialize() {
		isFilled = false;
	}

	@FXML
	void filterExpenses(ActionEvent event) {

		Session session = HibernateUtil.getSession();

		Criteria criteria = session.createCriteria(Expense.class);
		if (amount.getText() != null && !amount.getText().isEmpty()) {
			criteria.add(Restrictions.eq("amount",
					Long.parseLong(amount.getText())));
		}
		if (expenseType.getSelectionModel().getSelectedItem() != null) {
			criteria.add(Restrictions.eq("type", expenseType
					.getSelectionModel().getSelectedItem()));
		}
		if (startDate.getValue() != null) {
			Date value = DateUtils.asDate(startDate.getValue());
			criteria.add(Restrictions.gt("date", value));
		}
		if (endDate.getValue() != null) {
			Date value = DateUtils.asDate(endDate.getValue());
			criteria.add(Restrictions.lt("date", value));
		}

		criteria.addOrder(Order.desc("id"));
		criteria.setMaxResults(1000);

		List<Expense> filteredExpenseList = criteria.list();
		ObservableList<Expense> expenseListData = FXCollections
				.observableList(filteredExpenseList);
		AllControllers.viewExpensesController.expensesTable
				.setItems(expenseListData);

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
					.setMaxResults(20).list();
			PrintLog.printLog("num of expenses types:" + resultList.size());
			ArrayList<String> allTypes = new ArrayList<String>();
			for (String next : resultList) {
				allTypes.add(next);
			}
			expenseType.setItems(FXCollections.observableArrayList(allTypes));
			isFilled = true;
		}
		PrintLog.printLog("mouse clicked");
	}

	@FXML
	void clearFilter(ActionEvent event) {
		PrintLog.printLog("Clearing form");
		FormUtil.clearNode(searchPane);
		ViewAnimalsMatingController.data = ViewAnimalsMatingController
				.getInitialTableData();
		AllControllers.viewAnimalsMatingController.animalsMatingTable
				.setItems(ViewAnimalsMatingController.data);
	}

}
