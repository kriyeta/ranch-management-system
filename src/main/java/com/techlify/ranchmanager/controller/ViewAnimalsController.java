package com.techlify.ranchmanager.controller;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.ResizeFeatures;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.hibernate.Session;

import com.techlify.ranchmanager.common.AllPaths;
import com.techlify.ranchmanager.dao.Animal;
import com.techlify.ranchmanager.dao.AnimalType;
import com.techlify.ranchmanager.dao.Photo;
import com.techlify.ranchmanager.main.PrimarySatge;
import com.techlify.ranchmanager.util.DateUtils;
import com.techlify.ranchmanager.util.FXMLUtility;
import com.techlify.ranchmanager.util.HibernateUtil;
import com.techlify.ranchmanager.util.PrintLog;

/**
 * @author kamal
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ViewAnimalsController implements Initializable {

	@FXML
	private TableView<Animal> animalsTable;

	private ObservableList data;

	@FXML
	private TableColumn dateOfBirth;
	
	@FXML
	private VBox filterBox;

	@FXML
	private TextField filterText;

	@FXML
	private TableColumn gender;

	@FXML
	private TableColumn id;

	@FXML
	private TableColumn numbers;

	@FXML
	private TableColumn type;

	private Stage viewDetailStage;

	private ObservableList<Animal> getInitialTableData() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		List<Animal> resultList = session.createCriteria(Animal.class).list();

		ObservableList<Animal> animalsListData = FXCollections
				.observableList(resultList);

		return animalsListData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */

	public void initialize(URL location, ResourceBundle resources) {
		animalsTable
				.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>() {
					public Boolean call(ResizeFeatures param) {
						return true;
					}
				});
		id.setCellValueFactory(new PropertyValueFactory<Animal, Long>("id"));
		numbers.setCellValueFactory(new PropertyValueFactory<Animal, Long>(
				"numbers"));
		type.setCellValueFactory(new PropertyValueFactory<Animal, AnimalType>(
				"typeId"));
		gender.setCellValueFactory(new PropertyValueFactory<Animal, Long>(
				"gender"));
		dateOfBirth.setCellValueFactory(new PropertyValueFactory<Animal, Date>(
				"dateOfBirth"));

		// adding actions column

		TableColumn col_action = new TableColumn("DETAILS");
		col_action.setMinWidth(100);
		col_action.setSortable(false);

		col_action
				.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Animal, Boolean>, ObservableValue<Boolean>>() {

					public ObservableValue<Boolean> call(
							TableColumn.CellDataFeatures<Animal, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});

		col_action
				.setCellFactory(new Callback<TableColumn<Animal, Boolean>, TableCell<Animal, Boolean>>() {

					public TableCell<Animal, Boolean> call(
							TableColumn<Animal, Boolean> p) {
						return new ButtonCell();
					}

				});
		animalsTable.getColumns().add(col_action);

		data = getInitialTableData();
		animalsTable.setItems(data);

		// adding filter listener to filter text field
		filterText.textProperty().addListener(new InvalidationListener() {

			public void invalidated(Observable o) {

				if (filterText.textProperty().get().isEmpty()) {

					animalsTable.setItems(data);

					return;

				}

				ObservableList<Animal> tableItems = FXCollections
						.observableArrayList();

				ObservableList<TableColumn<Animal, ?>> cols = animalsTable
						.getColumns();

				for (int i = 0; i < data.size(); i++) {

					for (int j = 0; j < cols.size(); j++) {

						TableColumn col = cols.get(j);

						String cellValue = null;
						try {
							cellValue = col.getCellData(data.get(i)).toString();
						} catch (Exception e) {
						}
						if (cellValue != null) {
							cellValue = cellValue.toLowerCase();

							if (cellValue.contains(filterText.textProperty()
									.get().toLowerCase())) {

								tableItems.add((Animal) data.get(i));

								break;

							}
						}

					}

				}

				animalsTable.setItems(tableItems);

			}

		});
	}

	// Define the button cell
	private class ButtonCell extends TableCell<Animal, Boolean> {
		final Button cellButton = new Button("View");

		ButtonCell() {

			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent t) {
					// showing detailed view on view button clicked
					PrintLog.printLog("View button clicked ");
					Animal animal = (Animal) ButtonCell.this.getTableView()
							.getItems().get(ButtonCell.this.getIndex());
					PrintLog.printLog(animal);
					showDetailedView(animal);

				}

				private void showDetailedView(Animal animal) {
					// building pop up
					if (viewDetailStage == null) {
						viewDetailStage = getPopupScene();
					}
					AnchorPane anchorPane = new AnchorPane();
					anchorPane
							.getStylesheets()
							.add(this
									.getClass()
									.getResource(
											AllPaths.ANIMAL_DETAILED_VIEW_STYLESHEET)
									.toExternalForm());

					// details main container
					VBox vBox = new VBox();
					vBox.setSpacing(10);
					vBox.setId("veiwDetailsVBox");

					FXMLUtility.setAnchorToZero(anchorPane, vBox);
					anchorPane.getChildren().add(vBox);
					Label hederTitle = new Label(
							"DETAILED VIEW FOR ANIMAL WITH ID "
									+ animal.getId());
					hederTitle.setId("hederTitle");
					vBox.getChildren().add(hederTitle);

					// parse animal object
					Long currentAnimalId = animal.getId();
					Long currentAnimalNumbers = animal.getNumbers();
					Date currentAnimalDateOfBirth = animal.getDateOfBirth();
					String currentAnimalGender = animal.getGender();
					AnimalType currentAnimalTypeId = animal.getTypeId();
					List<Photo> currentAnimalPhotos = animal.getPhotos();

					// build animal details gui

					// showing attributes
					GridPane gridPane = new GridPane();
					gridPane.setId("viewAnimalDetailsGridPane");
					gridPane.setHgap(15);
					gridPane.setVgap(2);
					gridPane.add(new Text("ID"), 0, 0);
					gridPane.add(new Text(":"), 1, 0);
					gridPane.add(new Text(String.valueOf(currentAnimalId)), 2,
							0);
					gridPane.add(new Text("NUMBERS"), 0, 1);
					gridPane.add(new Text(":"), 1, 1);
					gridPane.add(
							new Text(String.valueOf(currentAnimalNumbers)), 2,
							1);
					gridPane.add(new Text("DATE OF BIRTH"), 0, 2);
					gridPane.add(new Text(":"), 1, 2);
					gridPane.add(
							new Text(DateUtils.asLocalDate(
									currentAnimalDateOfBirth).toString()), 2, 2);
					gridPane.add(new Text("GENDER"), 0, 3);
					gridPane.add(new Text(":"), 1, 3);
					gridPane.add(new Text(currentAnimalGender), 2, 3);
					gridPane.add(new Text("TYPE"), 0, 4);
					gridPane.add(new Text(":"), 1, 4);
					gridPane.add(new Text(currentAnimalTypeId.getName()), 2, 4);
					vBox.getChildren().add(gridPane);

					// showing images
					
					HBox photosHBox = new HBox();
					photosHBox.setMaxWidth(600);
					for (Photo photo : currentAnimalPhotos) {
						VBox photoVBox = new VBox();
						photoVBox.getChildren().add(
								new Text("ID: " + photo.getId()));
						byte[] animalImage = photo.getPhoto();
						StackPane imageViewPane = new StackPane();
						imageViewPane.setId("animalImagePane");
						ImageView imageView = new ImageView();
						imageView.setImage(new Image(new ByteArrayInputStream(
								animalImage)));
						imageView.setFitWidth(150);
						imageView.setFitHeight(200);
						imageView.setPreserveRatio(false);
						imageViewPane.getChildren().add(imageView);
						photoVBox.getChildren().add(imageViewPane);
						photosHBox.getChildren().add(photoVBox);
					}
					ScrollPane scrollPane	=	new ScrollPane(photosHBox);
					scrollPane.setId("photosScrollPane");
					scrollPane.setFitToHeight(true);
					scrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
					scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
					vBox.getChildren().add(scrollPane);
					Scene dialogScene = new Scene(anchorPane,
							USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
					viewDetailStage.setScene(dialogScene);
					viewDetailStage.show();

				}

				/**
				 * @return
				 */
				private Stage getPopupScene() {
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