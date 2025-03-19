package com.example.dataModel;

import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int idCounter = 1;

    private int idEmployee;
    private String name;
    private List<Task> tasks;

    public Employee(String name) {
        this.idEmployee = idCounter++;
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public static void updateIdCounter(ObservableList<Employee> employees) {
        int maxId = 0;
        for (Employee e : employees) {
            if (e.getIdEmployee() > maxId) {
                maxId = e.getIdEmployee();
            }
        }
        idCounter = maxId + 1;
    }
}
