package com.ems.energymanagementsystem;

import com.ems.energymanagementsystem.model.Customer;
import com.ems.energymanagementsystem.model.EMSData;
import com.ems.energymanagementsystem.model.Invoice;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class DashboardController {

    @FXML
    private DialogPane mainPanel;
    @FXML
    private Label labelElectTariffName;
    @FXML
    private Label labelGasTariffName;
    @FXML
    private Label labelElectUsage;
    @FXML
    private Label labelGasUsage;
    @FXML
    private Label labelElectRate;
    @FXML
    private Label labelGasRate;

    public void initialize() {
    }

    /**
     * Method to populate the Dashboard interface.
     */
    public void dashboardData(String selectedItem) {
        int annualUsage = 0;
        ArrayList<Invoice> invoices = EMSData.getInstance().getInvoice();
        ArrayList<Customer> customers = EMSData.getInstance().findCustomer();
        for (Invoice invoice : invoices) {
            if (invoice.getCustomerFullName().equals(selectedItem)) {
                annualUsage += invoice.getKilowatt();
                labelElectUsage.setText(String.valueOf(annualUsage).concat(" kWh"));
                labelGasUsage.setText(String.valueOf(annualUsage).concat(" kWh"));
                labelElectRate.setText(String.valueOf(invoice.getRate()).concat("p"));
                labelGasRate.setText(String.valueOf(invoice.getRate()).concat("p"));
            }
        }

        for (Customer customer : customers) {
            if (customer.getFullName().equals(selectedItem)) {
                labelElectTariffName.setText(customer.getTariff());
                labelGasTariffName.setText(customer.getTariff());
            }
        }
    }
}
