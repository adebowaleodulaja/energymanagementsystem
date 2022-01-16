package com.ems.energymanagementsystem.model;

import com.ems.energymanagementsystem.util.StoreAndRetrieveObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * This class purpose is to serve as a Data manipulation class.<br />
 * <br />I created a series of helper methods here to make things easier for me.
 *
 * @author Adebowale Odulaja
 */
public class EMSData {

    private static final Properties config = new Properties();

    private static final EMSData instance = new EMSData();
    private static final String USER_ACCOUNT_FILE_NAME = config.getProperty("useraccount.file");
    private static final String TARIFF_FILE_NAME = config.getProperty("tariff.file");
    private static final String CUSTOMER_FILE_NAME = config.getProperty("customer.file");
    private static final String INVOICE_FILE_NAME = config.getProperty("invoice.file");
    private final ArrayList<UserAccount> userAccountList = new ArrayList<>();
    private final ArrayList<Tariff> tariffs = new ArrayList<>();
    private final ArrayList<Customer> newCustomer = new ArrayList<>();
    private final ArrayList<Invoice> newInvoice = new ArrayList<>();
    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    /**
     * There's no intention to instantiate this class, hence the private Constructor
     */
    private EMSData() {
        try {
            config.load(new FileInputStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return An instance of EMData class
     */
    public static EMSData getInstance() {
        return instance;
    }

    /**
     * Serialize User Account details.
     *
     * @param userAccount A new instance of UserAccount
     */
    public void saveUserAccountToFile(UserAccount userAccount) {
        StoreAndRetrieveObject<UserAccount> obj = new StoreAndRetrieveObject<>();
        userAccountList.add(userAccount);
        obj.serializeData(userAccountList, USER_ACCOUNT_FILE_NAME);
    }

    /**
     * Deserialize User Account details.
     */
    public void loadUserAccountData() {
        StoreAndRetrieveObject<UserAccount> obj = new StoreAndRetrieveObject<>();
        userAccountList.addAll(obj.deserializeData(USER_ACCOUNT_FILE_NAME));
    }

    /**
     * Serialize Tariff details.
     *
     * @param tariff A new instance of Tariff
     */
    public void saveTariffToFile(Tariff tariff) {
        StoreAndRetrieveObject<Tariff> obj = new StoreAndRetrieveObject<>();
        tariffs.add(tariff);
        obj.serializeData(tariffs, TARIFF_FILE_NAME);
    }

    /**
     * Deserialize Tariff details.
     */
    public void loadTariffData() {
        StoreAndRetrieveObject<Tariff> obj = new StoreAndRetrieveObject<>();
        tariffs.addAll(obj.deserializeData(TARIFF_FILE_NAME));
    }

    /**
     * @return an ArrayList of Tariff
     */
    public ArrayList<Tariff> getTariffs() {
        return tariffs;
    }

    public void removeTariff(Tariff tariff) {
        tariffs.remove(tariff);
    }

    /**
     * Get list of customers.
     *
     * @return Returns a List of Customers with additional feature from JavaFx ObservableList interface.
     */
    public ObservableList<Customer> getCustomer() {
        customers.addAll(newCustomer);
        return customers;
    }

    public ArrayList<Customer> findCustomer() {
        return newCustomer;
    }

    /**
     * Serialize Customer details.
     *
     * @param customer A new instance of Customer.
     */
    public void saveCustomerToFile(Customer customer) {
        StoreAndRetrieveObject<Customer> customerObj = new StoreAndRetrieveObject<>();
        newCustomer.add(customer);
        customerObj.serializeData(newCustomer, CUSTOMER_FILE_NAME);
    }

    /**
     * Deserialize Customer details.
     */
    public void loadCustomerData() {
        StoreAndRetrieveObject<Customer> customerObj = new StoreAndRetrieveObject<>();
        newCustomer.addAll(customerObj.deserializeData(CUSTOMER_FILE_NAME));
    }

    public void removeCustomer(Customer customer) {
        newCustomer.remove(customer);
    }

    /**
     * Reloads the content of the table.
     */
    public void refreshTable() {
        customers.clear();
        getCustomer();
    }

    /**
     * Serialize a new invoice.
     *
     * @param invoice A new instance of Invoice
     */
    public void saveInvoiceToFile(Invoice invoice) {
        StoreAndRetrieveObject<Invoice> obj = new StoreAndRetrieveObject<>();
        newInvoice.add(invoice);
        obj.serializeData(newInvoice, INVOICE_FILE_NAME);
    }

    /**
     * Deserialize Invoice data.
     */
    public void loadInvoiceData() {
        StoreAndRetrieveObject<Invoice> obj = new StoreAndRetrieveObject<>();
        newInvoice.addAll(obj.deserializeData(INVOICE_FILE_NAME));
    }

    /**
     * @return an ArrayList of Invoice
     */
    public ArrayList<Invoice> getInvoice() {
        return newInvoice;
    }

}
