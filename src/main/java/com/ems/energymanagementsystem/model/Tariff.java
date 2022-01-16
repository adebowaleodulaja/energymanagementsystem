package com.ems.energymanagementsystem.model;

import java.io.Serializable;

/**
 * Model class for serializing Tariff data
 */
public class Tariff implements Serializable {

    private String tariffName;
    private String electricityRate;
    private String gasRate;

    public Tariff(){}

    public Tariff(String tariffName, String electricityRate, String gasRate){
        this.tariffName = tariffName;
        this.electricityRate = electricityRate;
        this.gasRate = gasRate;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public String getElectricityRate() {
        return electricityRate;
    }

    public void setElectricityRate(String electricityRate) {
        this.electricityRate = electricityRate;
    }

    public String getGasRate() {
        return gasRate;
    }

    public void setGasRate(String gasRate) {
        this.gasRate = gasRate;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "tariffName='" + tariffName + '\'' +
                ", electricityRate='" + electricityRate + '\'' +
                ", gasRate='" + gasRate + '\'' +
                '}';
    }
}
