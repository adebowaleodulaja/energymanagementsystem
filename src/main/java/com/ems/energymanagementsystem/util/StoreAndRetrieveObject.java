package com.ems.energymanagementsystem.util;

import java.io.*;
import java.util.ArrayList;

/**
 * Helper class for Serializing and Deserializing objects.
 */
public class StoreAndRetrieveObject<T> {
    private ArrayList<T> objectList;

    public StoreAndRetrieveObject() {
        objectList = new ArrayList<>();
    }

    /**
     * Generic method to Serialize object.
     */
    public void serializeData(ArrayList<T> listOfObjectToWrite, String FILE_NAME) {
        try {
            //Saving the object to file
            FileOutputStream file = new FileOutputStream(FILE_NAME);
            ObjectOutputStream outputStream = new ObjectOutputStream(file);
            for (int i = 0; i < listOfObjectToWrite.size(); i++) {
                outputStream.writeObject(listOfObjectToWrite);
            }

            outputStream.close();
            file.close();

            System.out.println("Object " + listOfObjectToWrite + " has been serialized");
        } catch (IOException ioException) {
            System.out.println("An error occurred whilst serializing the object, see more info below.\n");
            ioException.printStackTrace();
        }
    }

    /**
     * Generic method to Deserialize object.
     */
    public ArrayList<T> deserializeData(String FILE_NAME) {
        try {
            //Checking if the file exists
            File file = new File(FILE_NAME);
            FileInputStream fis = null;
            ObjectInputStream objectInputStream = null;
            if (file.exists()) {
                //Read content of the file
                fis = new FileInputStream(FILE_NAME);
                objectInputStream = new ObjectInputStream(fis);
            } else {
                System.out.println("File " + FILE_NAME + " does not exists");
            }
            if (file.length() > 0) {
                objectList = (ArrayList<T>) objectInputStream.readObject();
            } else {
                System.out.println("File " + FILE_NAME + " is empty.");
            }
            if (objectInputStream != null && fis != null) {
                objectInputStream.close();
                fis.close();
            }
            System.out.println("Object " + objectList + " has been deSerialized");

        } catch (IOException | ClassNotFoundException ioex) {
            System.out.println("An error occurred whilst deSerializing the object, see more info below.\n");
            ioex.printStackTrace();
        }
        return objectList;
    }
}
