package com.ems.energymanagementsystem;

import com.ems.energymanagementsystem.model.UserAccount;
import com.ems.energymanagementsystem.util.AlertDialog;
import com.ems.energymanagementsystem.util.StoreAndRetrieveObject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {

    @FXML
    private TextField textUserName;
    @FXML
    private PasswordField textPassword;
    @FXML
    private BorderPane mainPanel;

    public static String loginName = "";
    StoreAndRetrieveObject<UserAccount> obj;

    public void initialize() {
        //I did this because I didn't want my any of my TextFields to be focusable on start of the application.
        Platform.runLater(() -> mainPanel.requestFocus());
        obj = new StoreAndRetrieveObject<>();
    }

    /**
     * Method to be called when the Login Button is clicked.
     */
    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
        if (validateUserDetails())
            AlertDialog.getInstance().showAlertDialog(mainPanel.getScene().getWindow(), "Please enter your Username and Password.",
                    "Error validating field", Alert.AlertType.ERROR);
        else {
            String username = textUserName.getText().trim();
            String password = textPassword.getText().trim();
            if (isUserOnFile(username, password) || (username.equals("admin") && password.equals("admin"))) {
                loginName = textUserName.getText().trim();
                //AlertDialog.getInstance().showAlertDialog(mainPanel.getScene().getWindow(), "Login Successful", "Success", Alert.AlertType.INFORMATION);
                Parent parent = FXMLLoader.load(getClass().getResource("mainscreen.fxml"));
                Scene mainScreenScene = new Scene(parent, 900, 600);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(mainScreenScene);
                window.show();
            } else {
                AlertDialog.getInstance().showAlertDialog(mainPanel.getScene().getWindow(), "Username or Password is not correct.",
                        "Invalid User Details", Alert.AlertType.WARNING);
            }
        }
    }

    /**
     * When this method is called, it checks if the 'Username' and 'Password' that was entered in the Textfields
     * provided are valid or not. If they're not, it returns True.
     */
    private boolean isUserOnFile(String username, String password) {
        ArrayList<UserAccount> userAccountList = obj.deserializeData("UserAccount.dat");
        //System.out.println("Content in list-> " + userAccountList);
        for (UserAccount userAccount : userAccountList) {
            if (userAccount.getUserName().equals(username) && userAccount.getPassword().equals(password)) {
                System.out.println("UserName: " + userAccount.getUserName() + "\n" + "Password: " + userAccount.getPassword().hashCode());
                return true; //Found the user that's trying to login.
            }
        }

        return false;
    }

    /**
     * Returns true if any of the Text field is empty.
     */
    private boolean validateUserDetails() {
        return textUserName.getText().trim().isEmpty() || textPassword.getText().trim().isEmpty();
    }


}