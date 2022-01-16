package com.ems.energymanagementsystem;

import com.ems.energymanagementsystem.model.EMSData;
import com.ems.energymanagementsystem.model.Invoice;
import com.ems.energymanagementsystem.util.AlertDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParsePosition;

public class GenerateInvoiceController implements ChangeListener<Boolean> {
    @FXML
    private ComboBox<String> comboPaymentStatus;
    @FXML
    private TextField textTariffType;
    @FXML
    private TextField textMeterNo;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private TextField textOpeningRead;
    @FXML
    private TextField textClosingRead;
    @FXML
    private TextField textKilowatt;
    @FXML
    private TextField textRate;
    @FXML
    private TextField textPrice;
    @FXML
    private TextField textCusFullName;
    @FXML
    private VBox container;


    public void initialize() {
        textTariffType.setEditable(false);
        textMeterNo.setEditable(false);
        textKilowatt.setEditable(false);
        textRate.setEditable(false);
        textPrice.setEditable(false);
        textCusFullName.setEditable(false);
        comboPaymentStatus.setEditable(false);

        textFormater(textOpeningRead);
        textFormater(textClosingRead);

        ObservableList<String> paymentStatus = FXCollections.observableArrayList("Paid", "Not Paid");
        comboPaymentStatus.setItems(paymentStatus);
        textClosingRead.focusedProperty().addListener(this);
    }

    /**
     * Method to create a new Invoice and serialize the data.
     */
    public void createNewInvoice() {
        String tariff = textTariffType.getText().trim();
        String meterNo = textMeterNo.getText().trim();
        String from = String.valueOf(dateFrom.getValue());
        String to = String.valueOf(dateTo.getValue());
        String period = from.concat(" - ").concat(to);
        String opening = textOpeningRead.getText().trim();
        String closing = textClosingRead.getText().trim();
        double kiloWatt = Double.parseDouble(textKilowatt.getText().trim());
        double rate = Double.parseDouble(textRate.getText().trim());
        double price = Double.parseDouble(textPrice.getText().trim());
        String fullName = textCusFullName.getText().trim();
        String paymentStatus = comboPaymentStatus.getSelectionModel().getSelectedItem();

        //This code is for getting the no of days between the period selected.
        //I convert the selected date to Epoch day then subtract the 'from' date from the 'to' date.
        int dayDiff = (int) Math.abs(dateFrom.getValue().toEpochDay() - dateTo.getValue().toEpochDay()) + 1;
        String noOfDays = String.valueOf(dayDiff);

        Invoice newInvoice = new Invoice(tariff, meterNo, period, opening, closing, kiloWatt, rate, price, fullName, paymentStatus, noOfDays);
        EMSData.getInstance().saveInvoiceToFile(newInvoice);
        System.out.println("New invoice details: " + newInvoice);
    }

    /**
     * Check if there is any emtpty field, if there's, the method returns True.
     */
    public boolean validateFields() {
        if (textOpeningRead.getText().trim().isEmpty() || textClosingRead.getText().trim().isEmpty() ||
                textKilowatt.getText().trim().isEmpty() || textPrice.getText().trim().isEmpty()) {
            AlertDialog.getInstance().showAlertDialog(container.getScene().getWindow(), "Some fields are empty", "Error", Alert.AlertType.ERROR);
            return true;
        } else if (dateFrom.getEditor().getText().trim().isEmpty() || dateTo.getEditor().getText().trim().isEmpty()) {
            AlertDialog.getInstance().showAlertDialog(container.getScene().getWindow(), "Please select date range", "No date selected", Alert.AlertType.ERROR);
            return true;
        } else if (comboPaymentStatus.getSelectionModel().isEmpty()) {
            AlertDialog.getInstance().showAlertDialog(container.getScene().getWindow(), "Please select a payment status", "Payment Status", Alert.AlertType.ERROR);
            return true;
        }
        return false;
    }

    /**
     * Populate some fields within the Invoice UI.
     */
    public void populateFields(String tariff, String meterNo, String rate, String customerName) {
        textTariffType.setText(tariff);
        textMeterNo.setText(meterNo);
        textRate.setText(rate);
        textCusFullName.setText(customerName);
    }

    /**
     * This method is provided by JavaFx ChanheListener, it monitors changes within the observable value
     * [in my case, I'm using it to monitor Closing Read text field]
     */
    @Override
    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldVal, Boolean newVal) {
        if (oldVal) {
            if (!(textClosingRead.getText().trim().isEmpty())) {
                if (Double.parseDouble(textClosingRead.getText().trim()) < Double.parseDouble(textOpeningRead.getText().trim())) {
                    AlertDialog.getInstance().showAlertDialog(container.getScene().getWindow(), "Closing read cannot be less than opening read.", "Reading Error", Alert.AlertType.ERROR);
                } else {
                    double kilowattUsed = Double.parseDouble(textClosingRead.getText()) - Double.parseDouble(textOpeningRead.getText());
                    double price = kilowattUsed * Double.parseDouble(textRate.getText());
                    this.textKilowatt.setText(String.format("%.3f", kilowattUsed));
                    this.textPrice.setText(String.format("%.3f", price));
                }
            } else
                AlertDialog.getInstance().showAlertDialog(container.getScene().getWindow(), "Clsoing read cannot be empty.", "Error", Alert.AlertType.ERROR);
        }
    }

    /**
     * Method for generating a new invoice and serialize the data.
     */
    public void viewInvoice() {
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.getDialogPane().getButtonTypes().add(cancelBtn);
//        dialog.initOwner(container.getScene().getWindow());
        dialog.setTitle("Invoice");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("invoice.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException ioex) {
            System.out.println("Error loading the dialog. Please see detailed error below \n");
            ioex.printStackTrace();
        }
        InvoiceController invoiceController = fxmlLoader.getController();
        int dayDiff = (int) Math.abs(dateFrom.getValue().toEpochDay() - dateTo.getValue().toEpochDay()) + 1;
        String noOfDays = String.valueOf(dayDiff).concat(" days");
        String standingChargeVal = String.valueOf(Integer.parseInt(noOfDays.replace(" days", "")) * .2263);
        String usageAmnt = "£".concat(textPrice.getText().trim());
        String standingCharge = String.format("%.3f", Double.parseDouble(standingChargeVal.replace("£", "")));

        double supCharge = Double.parseDouble(usageAmnt.replace("£", "")) + Double.parseDouble(standingCharge);
        double vat = supCharge * .05;
        double totalAmnt = supCharge + vat;
        String period = String.valueOf(dateFrom.getValue()).concat(" - ").concat(String.valueOf(dateTo.getValue()));
        String openingRead = textOpeningRead.getText().trim().concat(" E");
        String closingRead = textClosingRead.getText().trim().concat(" E");
        String kWh = textKilowatt.getText().trim().concat(" kWh");
        String price = "£".concat(textPrice.getText().trim());
        String meterNo = textMeterNo.getText().trim();
        String rate = textRate.getText().trim();

        invoiceController.setInvoiceValue("£".concat(String.valueOf(String.format("%.3f", totalAmnt))), noOfDays, "£".concat(standingCharge), usageAmnt, "£".concat(String.valueOf(String.format("%.3f", supCharge))), "£".concat(String.valueOf(String.format("%.3f", vat))), meterNo, period, openingRead, closingRead, kWh, kWh, period, kWh, rate, price, price);

        dialog.showAndWait();
    }

    /**
     * Formats the TextField such that only valid digit and a decimal point is allowed.
     */
    public void textFormater(TextField textField) {
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
