package com.techlify.ranchmanager.controller;

import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.techlify.ranchmanager.common.AllControllers;
import com.techlify.ranchmanager.dao.Animal;
import com.techlify.ranchmanager.dao.AnimalsMating;
import com.techlify.ranchmanager.util.DateUtils;
import com.techlify.ranchmanager.util.FormUtil;
import com.techlify.ranchmanager.util.HibernateUtil;
import com.techlify.ranchmanager.util.PrintLog;

/**
 * @author kamal
 *
 */
public class SearchAnimalsMatingController {

	@FXML
    private TextField femaleNumber;

    @FXML
    private TextField maleNumber;

    @FXML
    private AnchorPane searchPane;

    @FXML
    private VBox filterBox;

    @FXML
    private Button clearFilter;

    @FXML
    private Button filterMatings;

    @FXML
    private DatePicker EndDateLowerRange;

    @FXML
    private DatePicker EndDateUpperRange;

    @FXML
    private DatePicker StartDateLowerRange;

    @FXML
    private DatePicker StartDateUpperRange;

    @FXML
    void filterMatings(ActionEvent event) {
    	Session session = HibernateUtil.getSession();
    	
    	Criteria criteriaMaleAnimal = session.createCriteria(Animal.class);
    	criteriaMaleAnimal.add(Restrictions.eq("numbers", maleNumber.getText()));
    	List maleList = criteriaMaleAnimal.list();
    	Long maleAnimalId	=	null;
    	Animal maleAnimal	= null;
    	try {
			maleAnimal	=	(Animal) maleList.get(0);
			maleAnimalId	=	maleAnimal.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	Criteria criteriaFemaleAnimal = session.createCriteria(Animal.class);
    	criteriaFemaleAnimal.add(Restrictions.eq("numbers", femaleNumber.getText()));
    	List femaleList = criteriaFemaleAnimal.list();
    	Long femaleAnimalId	=	null;
    	Animal femaleAnimal = null;
    	try {
			femaleAnimal	=	(Animal) femaleList.get(0);
			femaleAnimalId	=	femaleAnimal.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	Criteria criteria = session.createCriteria(AnimalsMating.class);
    	if(maleAnimalId!=null){
    		criteria.add(Restrictions.eq("maleParent",maleAnimal));
    	}
    	if(femaleAnimalId!=null){
    		criteria.add(Restrictions.eq("femaleParent",femaleAnimal));
    	}
    	if(StartDateLowerRange.getValue()!=null){
    		Date value = DateUtils.asDate(StartDateLowerRange.getValue());
    		criteria.add(Restrictions.gt("startDate",value));
    	}
    	if(StartDateUpperRange.getValue()!=null){
    		Date value = DateUtils.asDate(StartDateUpperRange.getValue());
    		criteria.add(Restrictions.lt("startDate",value));
    	}
    	if(EndDateLowerRange.getValue()!=null){
    		Date value = DateUtils.asDate(EndDateLowerRange.getValue());
    		criteria.add(Restrictions.gt("endDate",value));
    	}
    	if(EndDateUpperRange.getValue()!=null){
    		Date value = DateUtils.asDate(EndDateUpperRange.getValue());
    		criteria.add(Restrictions.lt("endDate",value));
    	}
    	criteria.addOrder(Order.desc("id"));
    	criteria.setMaxResults(1000);
    	
    	List<AnimalsMating> filteredAnimalsMatingList = criteria.list();
    	ObservableList<AnimalsMating> animalsMatingListData = FXCollections
				.observableList(filteredAnimalsMatingList);
		AllControllers.viewAnimalsMatingController.animalsMatingTable
				.setItems(animalsMatingListData);

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
