package com.ems.energymanagementsystem;

import com.ems.energymanagementsystem.model.EMSData;
import com.ems.energymanagementsystem.model.Tariff;
import com.ems.energymanagementsystem.util.AlertDialog;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.List;

public class TariffController {

    @FXML
    private TextField textName;
    @FXML
    private TextField textElectricityRate;
    @FXML
    private TextField textGasRate;
    @FXML
    private DialogPane parentContainer;

    private List<Tariff> tariffs;

    public void initialize() {
        rateFormatter(textElectricityRate);
        rateFormatter(textGasRate);
        tariffs = EMSData.getInstance().getTariffs();
    }

    /**
     * Saves a new Tariff to the file.
     */
    public void newTariff() {
        String tariffName = textName.getText().trim();
        String electricityRate = textElectricityRate.getText().trim();
        String gasRate = textGasRate.getText().trim();

        Tariff newTariff = new Tariff(tariffName, electricityRate, gasRate);
        EMSData.getInstance().saveTariffToFile(newTariff);
        System.out.println("New tariff details: \n" + newTariff);
    }

    /**
     * Sets the Tariff fields for editing purposes only.
     */
    public void setValuesForEditing(String tariffName) {
        boolean found = false;
        textName.setEditable(false);
        for (Tariff tariff : tariffs) {
            if (tariff.getTariffName().equals(tariffName)) {
                textName.setText(tariff.getTariffName());
                textElectricityRate.setText(tariff.getElectricityRate());
                textGasRate.setText(tariff.getGasRate());
                found = true;
            }
        }
        if (!found)
            AlertDialog.getInstance().showAlertDialog("No such tariff name", "Tariff not found", Alert.AlertType.INFORMATION);

    }

    /**
     * Serialize the modified Tariff. I loop through the existing Tariffs then
     * if it matches the name entered by the user, I remove the old value and save the new.
     */
    public void saveEditedTariff(String tariffToRemove) {
        String tariffName = textName.getText().trim();
        String electricityRate = textElectricityRate.getText().trim();
        String gasRate = textGasRate.getText().trim();

        for (Tariff tariff : tariffs) {
            if (tariff.getTariffName().equals(tariffToRemove)) {
                EMSData.getInstance().removeTariff(tariff);
                break;
            }
        }
        Tariff editedTariff = new Tariff(tariffName, electricityRate, gasRate);
        EMSData.getInstance().saveTariffToFile(editedTariff);

    }

    /**
     * @return True, if there's any field that's empty.
     */
    public boolean areAllFiledValid() {
        if (textName.getText().trim().isEmpty() || textElectricityRate.getText().trim().isEmpty() || textGasRate.getText().trim().isEmpty()) {
            AlertDialog.getInstance().showAlertDialog(parentContainer.getScene().getWindow(), "All fields are required", "Error validating field", Alert.AlertType.ERROR);
            return true;
        }
        return false;
    }

    /**
     * Formats the TextField such that only a valid digit and decimal point is allowed.
     */
    public void rateFormatter(TextField textField) {
        DecimalFormat format = new DecimalFormat("#.0");
        textField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().isEmpty()) {
                return change;
            }
            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(change.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < change.getControlNewText().length())
                return null;
            else
                return change;
        }));
    }
}
