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
import org.hibernate.criterion.Restrictions;

import com.techlify.ranchmanager.common.AllControllers;
import com.techlify.ranchmanager.dao.Animal;
import com.techlify.ranchmanager.dao.AnimalType;
import com.techlify.ranchmanager.util.DateUtils;
import com.techlify.ranchmanager.util.FormUtil;
import com.techlify.ranchmanager.util.HibernateUtil;
import com.techlify.ranchmanager.util.PrintLog;

/**
 * @author kamal
 *
 */
public class SearchAnimalsController {

	@FXML
    private TextField animalNumber;

    @FXML
    private AnchorPane viewBox;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private VBox filterBox;

    @FXML
    private ComboBox<AnimalType> animalType;

    @FXML
    private DatePicker animalsDob;
    
    @FXML
    private AnchorPane searchPane;

    @FXML
    private Button searchAnimals;
    
    @FXML
    private Button clearFilter;
    
    static boolean isFilled = false;
    
    @FXML
	private void initialize() {
		
		// setting genders
		ArrayList<String> allGenders = new ArrayList<String>();
		allGenders.add("Male");
		allGenders.add("Female");
		gender.setItems(FXCollections.observableArrayList(allGenders));
		
		isFilled = false;
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
			animalType.setItems(FXCollections.observableArrayList(allTypes));
			isFilled = true;
		}
		PrintLog.printLog("mouse clicked");
	}

    @FXML
    void searchAnimals(ActionEvent event) {
    	Session session = HibernateUtil.getSession();
    	Criteria criteria = session.createCriteria(Animal.class);
    	if(animalNumber.getText()!=null && !animalNumber.getText().isEmpty()){
    		criteria.add(Restrictions.eq("numbers",animalNumber.getText()));
    	}
    	if(animalType.getSelectionModel().getSelectedItem()!=null){
    		criteria.add(Restrictions.eq("typeId",animalType.getSelectionModel().getSelectedItem()));
    	}
    	if(gender.getSelectionModel().getSelectedItem()!=null){
    		criteria.add(Restrictions.eq("gender",gender.getSelectionModel().getSelectedItem()));
    	}
    	if(animalsDob.getValue()!=null){
    		Date value = DateUtils.asDate(animalsDob.getValue());
    		criteria.add(Restrictions.eq("dateOfBirth",value));
    	}
    	criteria.addOrder(Order.desc("id"));
    	criteria.setMaxResults(1000);
    	List<Animal> filteredAnimalList = criteria.list();
    	ObservableList<Animal> animalsListData = FXCollections
				.observableList(filteredAnimalList);
		AllControllers.viewAnimalsController.animalsTable
				.setItems(animalsListData);
    }
    
    @FXML
    void clearFilter(ActionEvent event) {
		PrintLog.printLog("Clearing form");
		FormUtil.clearNode(searchPane);
		ViewAnimalsController.data = ViewAnimalsController
				.getInitialTableData();
		AllControllers.viewAnimalsController.animalsTable
				.setItems(ViewAnimalsController.data);
	}
    
    

}
