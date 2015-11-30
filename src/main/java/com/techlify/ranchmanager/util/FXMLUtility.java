package com.techlify.ranchmanager.util;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author kamal
 *
 */
public class FXMLUtility {
	
	/**
	 * @param fxmlResourcePath
	 * @param stage
	 * @return 
	 */
	public static void loadFxml(String fxmlResourcePath, Stage stage) {
		PrintLog.printLog(fxmlResourcePath);
		URL location=FXMLUtility.class.getResource(fxmlResourcePath);
		PrintLog.printLog(location);
		FXMLLoader fxmlLoader=new FXMLLoader(location);
		Pane root=null;
		try {
		  root=(Pane)fxmlLoader.load();
		}
      catch (  IOException e) {
		  e.printStackTrace();
		}
		Scene scene	=	new Scene(root);
		stage.setScene(scene);
		
		stage.setMaximized(true);
	}
	
	/**
	 * @param fxmlResourcePath
	 * @param stage
	 * @return 
	 * @return 
	 */
	public static Node loadFxmlOnStackPane(String fxmlResourcePath, StackPane stackPane) {
		URL location=FXMLUtility.class.getResource(fxmlResourcePath);
		System.out.println(location);
		FXMLLoader fxmlLoader=new FXMLLoader(location);
		Node root = null;
		try {
			root=fxmlLoader.load();
		}
		catch (  IOException e) {
			e.printStackTrace();
		}
		stackPane.getChildren().clear();
		stackPane.getChildren().add(root);
		return root;
	}
	
	/**
	 * @param fxmlResourcePath
	 * @param stage
	 * @return 
	 * @return 
	 */
	public static Node loadFxmlOnComponent(String fxmlResourcePath) {
		URL location=FXMLUtility.class.getResource(fxmlResourcePath);
		System.out.println(location);
		FXMLLoader fxmlLoader=new FXMLLoader(location);
		Node root = null;
		try {
			root=fxmlLoader.load();
		}
		catch (  IOException e) {
			e.printStackTrace();
		}
		return root;
	}
	
	/**
	 * @param anchorPane
	 * @param vBox
	 */
	public static void setAnchorToZero(AnchorPane anchorPane, VBox vBox) {
		anchorPane.setTopAnchor(vBox, 0.0);
		anchorPane.setRightAnchor(vBox, 0.0);
		anchorPane.setBottomAnchor(vBox, 0.0);
		anchorPane.setLeftAnchor(vBox, 0.0);
	}

}
