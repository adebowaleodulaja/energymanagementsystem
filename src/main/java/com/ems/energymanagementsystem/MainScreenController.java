package com.ems.energymanagementsystem;

import com.ems.energymanagementsystem.model.Customer;
import com.ems.energymanagementsystem.model.EMSData;
import com.ems.energymanagementsystem.model.Tariff;
import com.ems.energymanagementsystem.util.AlertDialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class MainScreenController {

    @FXML
    private Label loginName;
    @FXML
    private BorderPane mainContainer;
    @FXML
    private TableView<Customer> customerTable;

    private ArrayList<Tariff> tariffs;

    public void initialize() {
        loginName.setText(LoginController.loginName);
        EMSData.getInstance().loadUserAccountData();
        EMSData.getInstance().loadTariffData();
        EMSData.getInstance().loadCustomerData();
        EMSData.getInstance().loadInvoiceData();
        customerTable.setItems(EMSData.getInstance().getCustomer());
        tariffs = EMSData.getInstance().getTariffs();
    }

    /**
     * Method for creating a new UserAccount.
     */
    @FXML
    public void newUserAccount() {
        ButtonType okBtn = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Dialog<ButtonType> userAccountDialog = DialogBuilder("New User Account", okBtn, cancelBtn);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("useraccount.fxml"));
        try {
            userAccountDialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException ioex) {
            System.out.println("Error loading the dialog. Please see detailed error below \n");
            ioex.printStackTrace();
        }
        UserAccountController userAccountController = fxmlLoader.getController();
        final Button btnOK = (Button) userAccountDialog.getDialogPane().lookupButton(okBtn);
        btnOK.addEventFilter(ActionEvent.ACTION, event -> {
            if (!userAccountController.validateFields()) {
                System.out.println("Waiting for method validateFields to return true...");
                event.consume();
            }
        });

        Optional<ButtonType> result = userAccountDialog.showAndWait();
        if (result.isPresent() && result.get().getText().equals("Save")) {
            userAccountController.newUserAccount();
        }
    }

    /**
     * Method for creating a new Customer information.
     */
    @FXML
    public void newCustomer() {
        ButtonType okBtn = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Dialog<ButtonType> customerDialog = DialogBuilder("New Customer", okBtn, cancelBtn);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("customer.fxml"));
        try {
            customerDialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException ioex) {
            System.out.println("Error loading the dialog. Please see detailed error below \n");
            ioex.printStackTrace();
        }
        CustomerController customerController = fxmlLoader.getController();
        final Button btnOK = (Button) customerDialog.getDialogPane().lookupButton(okBtn);
        btnOK.addEventFilter(ActionEvent.ACTION, event -> {
            if (customerController.areAllFiledValid()) {
                System.out.println("Waiting for method areAllFiledValid to return true...");
                event.consume();
            }
        });

        Optional<ButtonType> result = customerDialog.showAndWait();
        if (result.isPresent() && result.get().getText().equals("Save")) {
            //Customer customer = new Customer("New Guys", "074123456", "21, St. john avenue", "DayFlex", "Economy", "23457563");
            customerController.newCustomer();
            EMSData.getInstance().refreshTable();
        }
    }

    /**
     * This method is used for editing an existing Customer information.
     * Once the user is done editing and clicks the 'Save' button, it validates the fields
     * to be sure no important field is/are empty then serialize the information and reloads the
     * table to reflect the modified Customer information.
     */
    @FXML
    public void editCustomer() {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            AlertDialog.getInstance().showAlertDialog(mainContainer.getScene().getWindow(), "Please select a customer to modify.", "No customer selected", Alert.AlertType.INFORMATION);
            return;
        }
        ButtonType okBtn = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Dialog<ButtonType> customerDialog = DialogBuilder("Edit Customer", okBtn, cancelBtn);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("customer.fxml"));
        try {
            customerDialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException ioex) {
            System.out.println("Error loading the dialog. Please see detailed error below \n");
            ioex.printStackTrace();
        }
        CustomerController customerController = fxmlLoader.getController();
        final Button btnOK = (Button) customerDialog.getDialogPane().lookupButton(okBtn);
        btnOK.addEventFilter(ActionEvent.ACTION, event -> {
            if (customerController.areAllFiledValid()) {
                System.out.println("Waiting for method areAllFiledValid to return false...");
                event.consume();
            }
        });

        customerController.setValuesForEditing(selectedCustomer.getAccountNumber());
        Optional<ButtonType> result = customerDialog.showAndWait();
        if (result.isPresent() && result.get().getText().equals("Save")) {
            customerController.saveEditedCustomer(selectedCustomer.getAccountNumber());
            EMSData.getInstance().refreshTable();
        }
    }

    /**
     * This method is used for creatiing a new Tariff.
     */
    @FXML
    public void newTariff() {
        ButtonType okBtn = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Dialog<ButtonType> newTariffDialog = DialogBuilder("New Tariff", okBtn, cancelBtn);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("tariff.fxml"));
        try {
            newTariffDialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException ioex) {
            System.out.println("Error loading the dialog. Please see detailed error below \n");
            ioex.printStackTrace();
        }
        TariffController tariffController = fxmlLoader.getController();
        final Button btnOK = (Button) newTariffDialog.getDialogPane().lookupButton(okBtn);
        btnOK.addEventFilter(ActionEvent.ACTION, event -> {
            if (tariffController.areAllFiledValid()) {
                System.out.println("Waiting for method areAllFiledValid to return false...");
                event.consume();
            }
        });

        Optional<ButtonType> result = newTariffDialog.showAndWait();
        if (result.isPresent() && result.get().getText().equals("Save")) {
            tariffController.newTariff();
        }
    }

    /**
     * This method is for viewing Customer's dashboard.
     */
    @FXML
    public void dashboard() {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            AlertDialog.getInstance().showAlertDialog(mainContainer.getScene().getWindow(), "Please select a customer to view dashboard.", "No customer selected", Alert.AlertType.INFORMATION);
            return;
        }

        String selectedItem = selectedCustomer.getFullName();
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Dialog<ButtonType> dashboardDialog = DialogBuilder("Energy Usage Dashboard", null, cancelBtn);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dashboard.fxml"));
        try {
            dashboardDialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException ioex) {
            System.out.println("Error loading the dialog. Please see detailed error below \n");
            ioex.printStackTrace();
        }
        DashboardController dashboardController = fxmlLoader.getController();
        dashboardController.dashboardData(selectedItem);
        dashboardDialog.showAndWait();
    }

    /**
     * This method is used for generating an Invoice for a Customer.
     * It also persists the invoice details via Object serialization.
     */
    @FXML
    public void generateInvoice() {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            AlertDialog.getInstance().showAlertDialog(mainContainer.getScene().getWindow(), "Please select a customer to generate invoice.", "No customer selected", Alert.AlertType.INFORMATION);
            return;
        }
        String tariffName = selectedCustomer.getTariff();
        String customerName = selectedCustomer.getFullName();
        String meterNo = selectedCustomer.getAccountNumber();
        String rate = "";
        for (Tariff tariff : tariffs) {
            if (tariff.getTariffName().equals(tariffName)) {
                rate = tariff.getElectricityRate();
            }
        }
        ButtonType okBtn = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Dialog<ButtonType> generateInvoiceDialog = DialogBuilder("New Invoice", okBtn, cancelBtn);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("generateinvoice.fxml"));
        try {
            generateInvoiceDialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException ioex) {
            System.out.println("Error loading the dialog. Please see detailed error below \n");
            ioex.printStackTrace();
        }
        GenerateInvoiceController invoiceController = fxmlLoader.getController();
        final Button btnOK = (Button) generateInvoiceDialog.getDialogPane().lookupButton(okBtn);
        btnOK.addEventFilter(ActionEvent.ACTION, event -> {
            if (invoiceController.validateFields()) {
                System.out.println("Waiting for invoiceController.validateFields method to return false...");
                event.consume();
            }
        });

        invoiceController.populateFields(tariffName, meterNo, rate, customerName);
        Optional<ButtonType> result = generateInvoiceDialog.showAndWait();
        if (result.isPresent() && result.get().getText().equals("Save")) {
            invoiceController.createNewInvoice();
            invoiceController.viewInvoice();
        }
    }

    /**
     * This method is used for editing an existing Tariff.
     * Once the user is done editing and clicks the 'Save' button, it validates the fields
     * to be sure no important field is/are empty then serialize the information.
     */
    @FXML
    public void editTariff() {
        //String value = textInputDialog("Please enter tariff name", "Tariff Name");
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setHeaderText("Please enter tariff name");
        textInputDialog.setTitle("Tariff Name");
        textInputDialog.setGraphic(null);
        final Button lookupButton = (Button) textInputDialog.getDialogPane().lookupButton(ButtonType.OK);
        lookupButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (textInputDialog.getEditor().getText().trim().isEmpty()) {
                AlertDialog.getInstance().showAlertDialog("Please enter a valid input", "Error", Alert.AlertType.ERROR);
                event.consume();
            }
        });
        textInputDialog.showAndWait();
        String value = textInputDialog.getEditor().getText().trim();

        if (!value.isEmpty()) {
            ButtonType okBtn = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            Dialog<ButtonType> newTariffDialog = DialogBuilder("Update Tariff", okBtn, cancelBtn);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("tariff.fxml"));
            try {
                newTariffDialog.getDialogPane().setContent(fxmlLoader.load());
            } catch (IOException ioex) {
                System.out.println("Error loading the dialog. Please see detailed error below \n");
                ioex.printStackTrace();
            }
            TariffController tariffController = fxmlLoader.getController();
            final Button btnOK = (Button) newTariffDialog.getDialogPane().lookupButton(okBtn);
            btnOK.addEventFilter(ActionEvent.ACTION, event -> {
                if (tariffController.areAllFiledValid()) {
                    System.out.println("Waiting for method areAllFiledValid to return false...");
                    event.consume();
                }
            });
            tariffController.setValuesForEditing(value);

            Optional<ButtonType> result = newTariffDialog.showAndWait();
            if (result.isPresent() && result.get().getText().equals("Save")) {
                tariffController.saveEditedTariff(value);
            }
        }

    }

    @FXML
    public void about() {
        AlertDialog.getInstance().showAlertDialog(mainContainer.getScene().getWindow(), "This application was developed from scratch by\n[Adebowale Odulaja] MSc Computing 2021/2022 set.\nAll rights reserved.", "About Me", Alert.AlertType.INFORMATION);
    }

    /**
     * This is a helper method to reduce code repetition.
     */
    private Dialog<ButtonType> DialogBuilder(String title, ButtonType okBtn, ButtonType cancelBtn) {
        Dialog<ButtonType> dialog = new Dialog<>();
        if (okBtn != null)
            dialog.getDialogPane().getButtonTypes().add(okBtn);
        dialog.getDialogPane().getButtonTypes().add(cancelBtn);
        dialog.initOwner(mainContainer.getScene().getWindow());
        dialog.setTitle(title);

        return dialog;
    }

    @FXML
    public void exitApplication() {
        Platform.exit();
    }
}
