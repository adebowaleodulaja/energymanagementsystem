package com.ems.energymanagementsystem.model;

import java.io.Serializable;

/**
 * Model class for serializing Invoice details
 */
public class Invoice implements Serializable {
    private String type;
    private String meterNo;
    private String period;
    private String openingRead;
    private String closingRead;
    private double kilowatt;
    private double rate;
    private double price;
    private String customerFullName;
    private String paymentStatus;
    private String noOfDays;

    public Invoice() {
    }

    public Invoice(String type, String meterNo, String period, String openingRead, String closingRead, double kilowatt, double rate, double price, String customerFullName, String paymentStatus, String noOfDays) {
        this.type = type;
        this.meterNo = meterNo;
        this.period = period;
        this.openingRead = openingRead;
        this.closingRead = closingRead;
        this.kilowatt = kilowatt;
        this.rate = rate;
        this.price = price;
        this.customerFullName = customerFullName;
        this.paymentStatus = paymentStatus;
        this.noOfDays = noOfDays;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getOpeningRead() {
        return openingRead;
    }

    public void setOpeningRead(String openingRead) {
        this.openingRead = openingRead;
    }

    public String getClosingRead() {
        return closingRead;
    }

    public void setClosingRead(String closingRead) {
        this.closingRead = closingRead;
    }

    public double getKilowatt() {
        return kilowatt;
    }

    public void setKilowatt(double kilowatt) {
        this.kilowatt = kilowatt;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "type='" + type + '\'' +
                ", meterNo='" + meterNo + '\'' +
                ", period='" + period + '\'' +
                ", openingRead='" + openingRead + '\'' +
                ", closingRead='" + closingRead + '\'' +
                ", kilowatt=" + kilowatt +
                ", rate=" + rate +
                ", price=" + price +
                ", customerFullName='" + customerFullName + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", noOfDays='" + noOfDays + '\'' +
                '}';
    }
}