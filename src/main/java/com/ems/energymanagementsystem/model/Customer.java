package com.ems.energymanagementsystem.model;

import java.io.Serializable;

/**
 * Model class for serializing Customer data
 */
public class Customer implements Serializable {
    private String fullName;
    private String phoneNumber;
    private String address;
    private String tariff;
    private String meterType;
    private String accountNumber;

    public Customer(){

    }

    public Customer(String fullName, String phoneNumber, String address, String tariff, String meterType, String accountNumber) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.tariff = tariff;
        this.meterType = meterType;
        this.accountNumber = accountNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public String getMeterType() {
        return meterType;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "fullName=" + fullName +
                ", phoneNumber=" + phoneNumber +
                ", address=" + address +
                ", tariff=" + tariff +
                ", meterType=" + meterType +
                ", accountNumber=" + accountNumber +
                '}';
    }
}
