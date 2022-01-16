package com.ems.energymanagementsystem.util;

import javafx.scene.control.Alert;
import javafx.stage.Window;

/**
 * A Singleton class for creating AlertDialog within my application.<br />
 * <br/>Creates an instance of the class when the class is referenced so all public methods can be accessible.
 */
public class AlertDialog {

    private static final AlertDialog instance = new AlertDialog();

    public static AlertDialog getInstance() {
        return instance;
    }

    /**
     * Creates an instance of modal AlertDialog with a specific owner
     */
    public void showAlertDialog(Window window, String contentText, String title, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.initOwner(window);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * Creates an instance of modal AlertDialog without a specific owner
     */
    public void showAlertDialog(String contentText, String title, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
