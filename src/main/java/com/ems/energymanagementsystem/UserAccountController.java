package com.ems.energymanagementsystem;

import com.ems.energymanagementsystem.model.UserAccount;
import com.ems.energymanagementsystem.model.EMSData;
import com.ems.energymanagementsystem.util.AlertDialog;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class UserAccountController {

    @FXML
    private TextField textFName;
    @FXML
    private TextField textLName;
    @FXML
    private TextField textUsername;
    @FXML
    private TextField textPassword;
    @FXML
    private TextField textConfirmPassword;

    @FXML
    private DialogPane parentContainer;


    public void initialize() {
    }

    public void newUserAccount() {
        String firstName = textFName.getText().trim();
        String lastName = textLName.getText().trim();
        String username = textUsername.getText().trim();
        String password = textPassword.getText().trim();

        UserAccount newUserAccount = new UserAccount(firstName, lastName, username, password);
        EMSData.getInstance().saveUserAccountToFile(newUserAccount);
        System.out.println("New user details: \n" + newUserAccount);
    }

    /**
     * Checks if any of the fields is/are empty.
     * @return true, if there's none.
     */
    public boolean validateFields() {
        //Checks if the first character is in Uppercase then contains at least a number.
        String passwordRegex = "^([A-Z])(?=.*\\d).*";

        if (textFName.getText().trim().isEmpty() ||
                textLName.getText().trim().isEmpty() ||
                textUsername.getText().trim().isEmpty() ||
                textPassword.getText().trim().isEmpty() ||
                textConfirmPassword.getText().trim().isEmpty()) {
            AlertDialog.getInstance().showAlertDialog(parentContainer.getScene().getWindow(), "All fields are required", "Error validating field", Alert.AlertType.ERROR);
            return false;
        } else if (!(textConfirmPassword.getText().trim()).equals(textPassword.getText().trim())) {
            AlertDialog.getInstance().showAlertDialog(parentContainer.getScene().getWindow(), "Password does not match", "Validate Password", Alert.AlertType.ERROR);
            return false;
        } else if (!(textPassword.getText().trim().matches(passwordRegex)) || textPassword.getText().trim().length() < 8) {
            AlertDialog.getInstance().showAlertDialog(parentContainer.getScene().getWindow(), "Password must contain at least 1 UPPERCASE letter,\n1 NUMBER and must be minimum of 8 characters long", "Password Criteria", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }
}
