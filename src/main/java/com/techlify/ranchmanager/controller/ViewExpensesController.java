package com.techlify.ranchmanager.controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.techlify.ranchmanager.common.AllPaths;
import com.techlify.ranchmanager.dao.Expense;
import com.techlify.ranchmanager.main.PrimarySatge;
import com.techlify.ranchmanager.util.FXMLUtility;
import com.techlify.ranchmanager.util.HibernateUtil;
import com.techlify.ranchmanager.util.PrintLog;

/**
 * @author kamal
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ViewExpensesController implements Initializable {

	@FXML
	private TableColumn amount;

	@FXML
	private AnchorPane viewBox;

	@FXML
	private VBox filterBox;

	@FXML
	private TableColumn details;

	@FXML
	private TableColumn id;

	@FXML
	private TableColumn type;

	@FXML
	public TableView<Expense> expensesTable;
	
	@FXML
    private AnchorPane searchPane;

	@FXML
	private TableColumn dateAdded;

	private Stage editDialogBoxStage;

	public static ObservableList data;
	int EXPENSES_PER_PAGE = 15;

	public static ObservableList<Expense> getInitialTableData() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		List<Expense> resultList = session.createCriteria(Expense.class)
				.addOrder(Order.desc("id")).setMaxResults(1000).list();

		ObservableList<Expense> expensesListData = FXCollections
				.observableList(resultList);

		return expensesListData;
	}

	public void initialize(URL location, ResourceBundle resources) {
		FXMLUtility.loadFxmlOnAnchorPane(AllPaths.SEARCH_EXPENSE_PAGE, searchPane);
		id.setCellValueFactory(new PropertyValueFactory<Expense, Long>("id"));
		amount.setCellValueFactory(new PropertyValueFactory<Expense, Long>(
				"amount"));
		type.setCellValueFactory(new PropertyValueFactory<Expense, String>(
				"type"));
		details.setCellFactory(new Callback<TableColumn<Expense, String>, TableCell<Expense, String>>() {
			@Override
			public TableCell<Expense, String> call(
					TableColumn<Expense, String> param) {
				try {
					TableCell<Expense, String> cell = new TableCell<>();
					Text text = new Text();
					cell.setGraphic(text);
					cell.setPrefHeight(Region.USE_COMPUTED_SIZE);
					text.wrappingWidthProperty().bind(cell.widthProperty());
					text.textProperty().bind(cell.itemProperty());
					return cell;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		});
		details.setCellValueFactory(new PropertyValueFactory<Expense, String>(
				"details"));
		dateAdded
				.setCellValueFactory(new Callback<CellDataFeatures<Expense, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Expense, String> param) {
						try {
							Date dob = param.getValue().getDate();
							DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
							return new SimpleStringProperty(df.format(dob));
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
					}
				});

		// adding actions column
		TableColumn col_action = new TableColumn("Actions");
		col_action.setMinWidth(100);
		col_action.setSortable(false);

		col_action
				.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Expense, Boolean>, ObservableValue<Boolean>>() {

					public ObservableValue<Boolean> call(
							TableColumn.CellDataFeatures<Expense, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});

		col_action
				.setCellFactory(new Callback<TableColumn<Expense, Boolean>, TableCell<Expense, Boolean>>() {

					public TableCell<Expense, Boolean> call(
							TableColumn<Expense, Boolean> p) {
						return new ButtonCell();
					}

				});
		expensesTable.getColumns().add(col_action);

		data = getInitialTableData();

		// Adding pagination
		Pagination pagination = new Pagination(
				(data.size() / EXPENSES_PER_PAGE + 1), 0);
		pagination.setPageFactory(this::createPage);
		filterBox.getChildren().remove(expensesTable);
		filterBox.getChildren().add(new BorderPane(pagination));
	}
	
	private Node createPage(int pageIndex) {

        int fromIndex = pageIndex *EXPENSES_PER_PAGE;
        int toIndex = Math.min(fromIndex + EXPENSES_PER_PAGE, data.size());
        expensesTable.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));

        return new BorderPane(expensesTable);
    }

	private class ButtonCell extends TableCell<Expense, Boolean> {
		final Button cellButton = new Button("Edit");

		ButtonCell() {

			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent t) {
					// showing detailed view on view button clicked
					PrintLog.printLog("Edit button clicked ");
					Expense expense = (Expense) ButtonCell.this.getTableView()
							.getItems().get(ButtonCell.this.getIndex());
					EditExpenseController.currentExpense = expense;
					PrintLog.printLog(expense);
					showEditDialogBox(expense);

				}

				private void showEditDialogBox(Expense expense) {
					// building pop up
					if (editDialogBoxStage == null) {
						editDialogBoxStage = getEditPopupScene();
					}

					Node loadFxmlOnComponent = FXMLUtility
							.loadFxmlOnComponent(AllPaths.EDIT_EXPENSES_PAGE);

					// details main container
					VBox vBox = new VBox();
					vBox.setSpacing(10);
					vBox.setId("veiwDetailsVBox");
					vBox.getStylesheets().add(
							this.getClass()
									.getResource(AllPaths.FORM_STYLESHEET)
									.toExternalForm());
					vBox.getChildren().add(loadFxmlOnComponent);
					Scene dialogScene = new Scene(vBox, USE_COMPUTED_SIZE,
							USE_COMPUTED_SIZE);
					editDialogBoxStage.setScene(dialogScene);
					editDialogBoxStage.show();
				}

				/**
				 * @return
				 */
				private Stage getEditPopupScene() {
					Stage stage = new Stage();
					stage.initModality(Modality.NONE);
					stage.initOwner(PrimarySatge.getPrimaryStage());
					return stage;
				}
			});
		}

		// Display button if the row is not empty
		@Override
		protected void updateItem(Boolean t, boolean empty) {
			super.updateItem(t, empty);
			if (!empty) {
				setGraphic(cellButton);
			}
		}
	}

}
