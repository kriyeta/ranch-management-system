package com.techlify.ranchmanager.util;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FormUtil {
	
	public static void clearNode(Node node){
    	if(node instanceof TextField){
    		((TextField) node).clear();
    	}
    	if(node instanceof ComboBox){
    		((ComboBox) node).getSelectionModel().clearSelection();
    	}
    	if(node instanceof DatePicker){
    		((DatePicker) node).getEditor().clear();
    		((DatePicker) node).setValue(null);
    	}
    	if(node instanceof VBox){
    		ObservableList<Node> children = ((VBox) node).getChildren();
    		for(Node child: children){
    			clearNode(child);
    		}
    	}
    	if(node instanceof HBox){
    		ObservableList<Node> children = ((HBox) node).getChildren();
    		for(Node child: children){
    			clearNode(child);
    		}
    	}
    	if(node instanceof GridPane){
    		ObservableList<Node> children = ((GridPane) node).getChildren();
    		for(Node child: children){
    			clearNode(child);
    		}
    	}
    	if(node instanceof AnchorPane){
    		ObservableList<Node> children = ((AnchorPane) node).getChildren();
    		for(Node child: children){
    			clearNode(child);
    		}
    	}
    	if(node instanceof StackPane){
    		ObservableList<Node> children = ((StackPane) node).getChildren();
    		for(Node child: children){
    			clearNode(child);
    		}
    	}
    	
    }

}
