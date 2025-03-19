package com.example.businessLogic;

import com.example.dataModel.Employee;
import com.example.dataModel.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TasksManagement implements Serializable {

    private transient ObservableList<Employee> employees;
    private transient ObservableList<Task> unassignedTasks;

    private List<Employee> employeesData;
    private List<Task> unassignedTasksData;

    public TasksManagement() {
        employeesData = new ArrayList<>();
        unassignedTasksData = new ArrayList<>();
        employees = FXCollections.observableArrayList(employeesData);
        unassignedTasks = FXCollections.observableArrayList(unassignedTasksData);
    }

    public ObservableList<Employee> getEmployees() {
        return employees;
    }

    public ObservableList<Task> getUnassignedTasks() {
        return unassignedTasks;
    }

    public void addEmployee(Employee employee) {
        employeesData.add(employee);
        employees.add(employee);
    }

    public void addUnassignedTask(Task task) {
        unassignedTasksData.add(task);
        unassignedTasks.add(task);
    }

    public void removeUnassignedTask(Task task) {
        unassignedTasksData.remove(task);
        unassignedTasks.remove(task);
    }

    public void assignTaskToEmployee(Employee employee, Task task) {
        employee.addTask(task);
        removeUnassignedTask(task);
    }

    public double calculateEmployeeWorkDuration(Employee employee) {
        double totalDuration = 0;
        for (Task task : employee.getTasks()) {
            if ("Completed".equals(task.getStatus())) {
                totalDuration += task.estimateDuration();
            }
        }
        return totalDuration;
    }

    public void modifyTaskStatus(Employee employee, Task task, String newStatus) {
        if (employee.getTasks().contains(task)) {
            task.setStatus(newStatus);
        }
    }


    private void writeObject(ObjectOutputStream out) throws IOException {

        out.defaultWriteObject();
    }


    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        employees = FXCollections.observableArrayList(employeesData);
        unassignedTasks = FXCollections.observableArrayList(unassignedTasksData);
    }
}
