package com.ems.energymanagementsystem.test;

import com.ems.energymanagementsystem.model.Tariff;
import com.ems.energymanagementsystem.util.StoreAndRetrieveObject;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EMSDataTest {

    private final ArrayList<Tariff> tariffs = new ArrayList<>();
    StoreAndRetrieveObject<Tariff> obj;


    @org.junit.Before
    public void setUp() throws Exception {
        obj = new StoreAndRetrieveObject<>();
        tariffs.addAll(obj.deserializeData("Tariff.dat"));
    }

    @org.junit.Test
    public void saveTariffToFile() {
        /*EMSData.getInstance().saveTariffToFile(new Tariff("Flexible", "0.75", "0.25"));
        EMSData.getInstance().saveTariffToFile(new Tariff("Economy", "0.98", "0.45"));
        EMSData.getInstance().saveTariffToFile(new Tariff("Gold", "1.25", "1.10"));
        EMSData.getInstance().saveTariffToFile(new Tariff("Premium", "2.10", "1.87"));
        int expected = 0, actual = tariffs.size();
        assertEquals(expected, actual);*/
    }

    @org.junit.Test
    public void loadTariffData() {
        boolean tariffList = tariffs.size() > 0;
        assertTrue("true", tariffList);
    }

    @org.junit.Test
    public void getTariffs(){
        int expected = 4, actual = tariffs.size();
        assertEquals(expected, actual);
    }
}