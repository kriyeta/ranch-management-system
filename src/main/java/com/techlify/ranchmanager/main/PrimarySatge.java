package com.techlify.ranchmanager.main;

import javafx.stage.Stage;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author kamal
 *
 */
public class PrimarySatge {
	  
    private static Stage primaryStage;

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		PrimarySatge.primaryStage = primaryStage;
	}
  
}