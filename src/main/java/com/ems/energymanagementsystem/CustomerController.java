package com.ems.energymanagementsystem;

import com.ems.energymanagementsystem.model.Customer;
import com.ems.energymanagementsystem.model.EMSData;
import com.ems.energymanagementsystem.model.Tariff;
import com.ems.energymanagementsystem.util.AlertDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Random;

public class CustomerController implements ChangeListener<Boolean> {

    @FXML
    private TextField textFullName;
    @FXML
    private TextField textPhoneNumber;
    @FXML
    private TextField textAddress;
    @FXML
    private TextField textMeterType;
    @FXML
    private TextField textAccNumber;
    @FXML
    private ComboBox<String> comboBoxTariff;
    @FXML
    private DialogPane parentContainer;
    private ArrayList<Customer> customers;

    public void initialize() {
        textAccNumber.setEditable(false);
        ObservableList<String> tariffObservableList = FXCollections.observableArrayList();
        for (Tariff tariff : EMSData.getInstance().getTariffs()) {
            tariffObservableList.add(tariff.getTariffName());
        }
        comboBoxTariff.setItems(tariffObservableList);
        textFullName.focusedProperty().addListener(this);
         customers = EMSData.getInstance().findCustomer();
    }

    /**
     * Method used to create a new Customer.
     */
    public void newCustomer() {
        String fullName = textFullName.getText().trim();
        String phoneNumber = textPhoneNumber.getText().trim();
        String address = textAddress.getText().trim();
        String tariff = comboBoxTariff.getSelectionModel().getSelectedItem();
        String meterType = textMeterType.getText().trim();
        String accountNumber = textAccNumber.getText().trim();

        Customer newCustomer = new Customer(fullName, phoneNumber, address, tariff, meterType, accountNumber);
        EMSData.getInstance().saveCustomerToFile(newCustomer);
        System.out.println("New customer details: " + newCustomer);
    }

    /**
     * The method checks for important empty field.
     * @return True, if any of the field is empty.
     */
    public boolean areAllFiledValid() {
        if (textFullName.getText().trim().isEmpty() || textPhoneNumber.getText().trim().isEmpty() ||
                textAddress.getText().trim().isEmpty() || textMeterType.getText().trim().isEmpty() ||
                textAccNumber.getText().trim().isEmpty() || comboBoxTariff.getSelectionModel().isEmpty()) {
            AlertDialog.getInstance().showAlertDialog(parentContainer.getScene().getWindow(), "All fields are required", "Error validating field", Alert.AlertType.ERROR);
            return true;
        }

        return false;
    }

    /**
     * Set the Customer fields for editing purposes.
     */
    public void setValuesForEditing(String customerAccNo) {
        textAccNumber.setEditable(false);
        for (Customer customer : customers) {
            if (customer.getAccountNumber().equals(customerAccNo)) {
                textFullName.setText(customer.getFullName());
                textPhoneNumber.setText(customer.getPhoneNumber());
                textAddress.setText(customer.getAddress());
                textMeterType.setText(customer.getMeterType());
                textAccNumber.setText(customer.getAccountNumber());
                comboBoxTariff.getSelectionModel().select(customer.getTariff());
            }
        }
    }

    /**
     * Serialize the modified Customer data. I loop through the existing Customer data then
     * if a match is found, I remove the old value and save the new.
     */
    public void saveEditedCustomer(String customerToRemove) {
        String fullName = textFullName.getText().trim();
        String phoneNumber = textPhoneNumber.getText().trim();
        String address = textAddress.getText().trim();
        String tariff = comboBoxTariff.getSelectionModel().getSelectedItem();
        String meterType = textMeterType.getText().trim();
        String accountNumber = textAccNumber.getText().trim();

        for (Customer customer : customers) {
            if (customer.getAccountNumber().equals(customerToRemove)) {
                EMSData.getInstance().removeCustomer(customer);
                break;
            }
        }
        Customer editedCustomer = new Customer(fullName, phoneNumber, address, tariff, meterType, accountNumber);
        EMSData.getInstance().saveCustomerToFile(editedCustomer);

    }


    /**
     * This method generates account/meter number by getting the last4 digits of the Customer's full name,
     * then generate a random 6 digits number and concatenate both to form the Account/Meter number.
     */
    private String generateAccount_MeterNumber() {
        String acc_meter_number = "";
        String fullName = textFullName.getText().trim().toUpperCase();
        String last4CharacterOfFullName = fullName.substring(fullName.length() - 4);

        int rand = new Random().nextInt(999999);
        String convertToString = String.format("%06d", rand);

        acc_meter_number = acc_meter_number.concat(convertToString).concat(last4CharacterOfFullName);

        return acc_meter_number;
    }

    /**This method is provided by JavaFx ChangeListener, it monitors changes within the observable value
     * [in my case, I'm using it to monitor Full Name text field]
    */
    @Override
    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldVal, Boolean newVal) {
        if (oldVal) {
            if (!(textFullName.getLength() < 4))
                this.textAccNumber.setText(generateAccount_MeterNumber());
            else
                AlertDialog.getInstance().showAlertDialog(parentContainer.getScene().getWindow(), "Your full name cannot be less than 4 characters.", "Name too short", Alert.AlertType.ERROR);
        }
    }
}
