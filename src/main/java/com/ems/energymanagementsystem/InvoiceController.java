package com.ems.energymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InvoiceController {

    @FXML
    private Label labelTotalAmount;
    @FXML
    private Label labelNoOfDays;
    @FXML
    private Label labelStandingCharge;
    @FXML
    private Label labelUsageAmount;
    @FXML
    private Label labelSupplyCharge;
    @FXML
    private Label labelVAT;
    @FXML
    private Label labelMeterNo;
    @FXML
    private Label labelDate;
    @FXML
    private Label labelOpening;
    @FXML
    private Label labelClosing;
    @FXML
    private Label labelKWH;
    @FXML
    private Label totalkWh;
    @FXML
    private Label labelPeriod;
    @FXML
    private Label labelChargeskWh;
    @FXML
    private Label labelRate;
    @FXML
    private Label labelPrice;
    @FXML
    private Label totalPrice;

    public void initialize() {

    }


    /**
     * This method set the text for each of the dynamic fields within the Invoice interface.
     */
    public void setInvoiceValue(String labelTotalAmount, String labelNoOfDays, String labelStandingCharge, String labelUsageAmount, String labelSupplyCharge, String labelVAT, String labelMeterNo, String labelDate, String labelOpening, String labelClosing, String labelKWH, String totalkWh, String labelPeriod, String labelChargeskWh, String labelRate, String labelPrice, String totalPrice) {
        this.labelTotalAmount.setText(labelTotalAmount);
        this.labelNoOfDays.setText(labelNoOfDays);
        this.labelStandingCharge.setText(labelStandingCharge);
        this.labelUsageAmount.setText(labelUsageAmount);
        this.labelSupplyCharge.setText(labelSupplyCharge);
        this.labelVAT.setText(labelVAT);
        this.labelMeterNo.setText(labelMeterNo);
        this.labelDate.setText(labelDate);
        this.labelOpening.setText(labelOpening);
        this.labelClosing.setText(labelClosing);
        this.labelKWH.setText(labelKWH);
        this.totalkWh.setText(totalkWh);
        this.labelPeriod.setText(labelPeriod);
        this.labelChargeskWh.setText(labelChargeskWh);
        this.labelRate.setText(labelRate);
        this.labelPrice.setText(labelPrice);
        this.totalPrice.setText(totalPrice);
    }

}
