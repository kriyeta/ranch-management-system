package com.techlify.ranchmanager.main;

import javafx.application.Application;
import javafx.stage.Stage;

import com.techlify.ranchmanager.common.AllPaths;
import com.techlify.ranchmanager.util.FXMLUtility;

/**
 * @author kamal
 *
 */
public class StartApp extends Application {


	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		PrimarySatge.setPrimaryStage(primaryStage);
		primaryStage.setTitle("Ranch Management information system");
		FXMLUtility.loadFxml(AllPaths.MAIN_PAGE, primaryStage);
		PrimarySatge.getPrimaryStage().getScene().getStylesheets().add(
				this.getClass()
						.getResource(AllPaths.MAIN_STYLESHEET).toExternalForm());
    	PrimarySatge.getPrimaryStage().getScene().getStylesheets().add(
    			this.getClass()
    			.getResource(AllPaths.FORM_STYLESHEET).toExternalForm());
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
