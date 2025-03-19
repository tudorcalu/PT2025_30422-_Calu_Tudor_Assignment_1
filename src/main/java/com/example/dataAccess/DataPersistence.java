package com.example.dataAccess;

import com.example.businessLogic.TasksManagement;
import java.io.*;

public class DataPersistence {

    private static final String FILE_PATH = "/home/tudor/IdeaProjects/Assignment1/src/main/java/com/example/persistance";


    public static void saveData(TasksManagement tasksManagement) {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(tasksManagement);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static TasksManagement loadData() {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            TasksManagement tasksManagement = (TasksManagement) ois.readObject();
            System.out.println("Data loaded successfully.");
            return tasksManagement;
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found. Creating new instance.");
            return new TasksManagement();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
            return new TasksManagement();
        }
    }
}
