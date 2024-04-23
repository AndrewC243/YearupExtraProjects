package org.example;

import java.io.*;
import java.util.List;

public class FileHandler {
    public static Object readFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeFile(String fileName, Object object) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(object);
        }
        catch (IOException e) {
            try {new File(fileName).createNewFile();}
            catch (IOException e1) {
                System.out.println("Oopsie :(");
            }
        }
    }
}
