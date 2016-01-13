package com.techlify.ranchmanager.controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
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
import javafx.util.Callback;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.techlify.ranchmanager.common.AllPaths;
import com.techlify.ranchmanager.dao.Animal;
import com.techlify.ranchmanager.dao.AnimalsMating;
import com.techlify.ranchmanager.util.FXMLUtility;
import com.techlify.ranchmanager.util.HibernateUtil;

/**
 * @author kamal
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ViewAnimalsMatingController implements Initializable {

	@FXML
	private AnchorPane viewBox;

	@FXML
	private AnchorPane searchPane;

	@FXML
	private VBox filterBox;

	@FXML
	private TableColumn endDate;

	@FXML
	private TableColumn id;

	@FXML
	private TableColumn maleAnimal;

	@FXML
	private TableColumn femaleAnimal;

	@FXML
	private TableColumn startDate;

	@FXML
	public TableView<AnimalsMating> animalsMatingTable;

	public static ObservableList data;
	int ANIMALS_MATING_PER_PAGE	=	15;

	public void initialize(URL location, ResourceBundle resources) {
		FXMLUtility.loadFxmlOnAnchorPane(AllPaths.SEARCH_ANIMALS_MATING_PAGE,
				searchPane);
		id.setCellValueFactory(new PropertyValueFactory<AnimalsMating, Long>(
				"id"));

		maleAnimal
				.setCellFactory(new Callback<TableColumn<AnimalsMating, String>, TableCell<AnimalsMating, String>>() {
					@Override
					public TableCell<AnimalsMating, String> call(
							TableColumn<AnimalsMating, String> param) {
						try {
							TableCell<AnimalsMating, String> cell = new TableCell<>();
							Hyperlink hyperlink = new Hyperlink();
							hyperlink
									.setOnAction(new EventHandler<ActionEvent>() {

										@Override
										public void handle(ActionEvent event) {
											Hyperlink currentHyperlink = (Hyperlink) event
													.getSource();
											String animalNumber = currentHyperlink
													.getText();
											Session session = HibernateUtil
													.getSession();
											session.beginTransaction();
											List<Animal> resultList = session
													.createCriteria(
															Animal.class)
													.add(Restrictions.eq(
															"numbers",
															animalNumber))
													.list();
											ViewAnimalsController
													.showDetailedView(resultList
															.get(0));
										}
									});
							cell.setGraphic(hyperlink);
							cell.setPrefHeight(Region.USE_COMPUTED_SIZE);
							hyperlink.textProperty().bind(cell.itemProperty());
							return cell;
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
					}
				});

		maleAnimal
				.setCellValueFactory(new Callback<CellDataFeatures<AnimalsMating, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<AnimalsMating, String> param) {
						try {
							String maleParent = param.getValue()
									.getMaleParent().getNumbers();
							return new SimpleStringProperty(maleParent);
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
					}
				});

		femaleAnimal
				.setCellFactory(new Callback<TableColumn<AnimalsMating, String>, TableCell<AnimalsMating, String>>() {
					@Override
					public TableCell<AnimalsMating, String> call(
							TableColumn<AnimalsMating, String> param) {
						try {
							TableCell<AnimalsMating, String> cell = new TableCell<>();
							Hyperlink hyperlink = new Hyperlink();
							hyperlink
									.setOnAction(new EventHandler<ActionEvent>() {

										@Override
										public void handle(ActionEvent event) {
											Hyperlink currentHyperlink = (Hyperlink) event
													.getSource();
											String animalNumber = currentHyperlink
													.getText();
											Session session = HibernateUtil
													.getSession();
											session.beginTransaction();
											List<Animal> resultList = session
													.createCriteria(
															Animal.class)
													.add(Restrictions.eq(
															"numbers",
															animalNumber))
													.list();
											ViewAnimalsController
													.showDetailedView(resultList
															.get(0));
										}
									});
							cell.setGraphic(hyperlink);
							cell.setPrefHeight(Region.USE_COMPUTED_SIZE);
							hyperlink.textProperty().bind(cell.itemProperty());
							return cell;
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
					}
				});

		femaleAnimal
				.setCellValueFactory(new Callback<CellDataFeatures<AnimalsMating, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<AnimalsMating, String> param) {
						try {
							String femaleParent = param.getValue()
									.getFemaleParent().getNumbers();
							return new SimpleStringProperty(femaleParent);
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
					}
				});

		startDate
				.setCellValueFactory(new Callback<CellDataFeatures<AnimalsMating, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<AnimalsMating, String> param) {
						try {
							Date sd = param.getValue().getStartDate();
							DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
							return new SimpleStringProperty(df.format(sd));
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
					}
				});

		endDate.setCellValueFactory(new Callback<CellDataFeatures<AnimalsMating, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(
					CellDataFeatures<AnimalsMating, String> param) {
				try {
					Date ed = param.getValue().getEndDate();
					DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
					return new SimpleStringProperty(df.format(ed));
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		});

		data = getInitialTableData();
		animalsMatingTable.setItems(data);

		// Adding pagination
		Pagination pagination = new Pagination(
				(data.size() / ANIMALS_MATING_PER_PAGE + 1), 0);
		pagination.setPageFactory(this::createPage);
		filterBox.getChildren().remove(animalsMatingTable);
		filterBox.getChildren().add(new BorderPane(pagination));

	}
	
	private Node createPage(int pageIndex) {

        int fromIndex = pageIndex * ANIMALS_MATING_PER_PAGE;
        int toIndex = Math.min(fromIndex + ANIMALS_MATING_PER_PAGE, data.size());
        animalsMatingTable.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));

        return new BorderPane(animalsMatingTable);
    }

	public static ObservableList<AnimalsMating> getInitialTableData() {

		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		List<AnimalsMating> resultList = session
				.createCriteria(AnimalsMating.class).addOrder(Order.desc("id"))
				.setMaxResults(1000).list();

		ObservableList<AnimalsMating> animalsListData = FXCollections
				.observableList(resultList);

		return animalsListData;
	}

}
