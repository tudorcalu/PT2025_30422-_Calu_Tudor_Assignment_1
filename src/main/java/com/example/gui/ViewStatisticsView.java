package com.example.gui;

import com.example.businessLogic.TasksManagement;
import com.example.businessLogic.Utility;
import com.example.dataModel.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.Map;

public class ViewStatisticsView extends VBox {

    public ViewStatisticsView(TasksManagement tasksManagement) {
        setSpacing(10);
        setPadding(new Insets(10));

        Label titleLabel = new Label("View Statistics");

        Label employeesOver40Label = new Label("Employees with work duration over 40 hours:");
        ListView<String> employeesOver40List = new ListView<>();

        Label taskStatusLabel = new Label("Task Status Statistics:");
        ListView<String> taskStatusList = new ListView<>();

        Button refreshButton = new Button("Refresh Statistics");
        refreshButton.setOnAction(e -> {
            refreshEmployeesOver40(tasksManagement, employeesOver40List);
            refreshTaskStatusStatistics(tasksManagement, taskStatusList);
        });

        getChildren().addAll(titleLabel,
                employeesOver40Label, employeesOver40List,
                taskStatusLabel, taskStatusList,
                refreshButton);

        refreshEmployeesOver40(tasksManagement, employeesOver40List);
        refreshTaskStatusStatistics(tasksManagement, taskStatusList);
    }

    private void refreshEmployeesOver40(TasksManagement tasksManagement, ListView<String> listView) {
        ObservableList<String> items = FXCollections.observableArrayList(
                Utility.getEmployeesOver40Hours(tasksManagement)
        );
        listView.setItems(items);
    }

    private void refreshTaskStatusStatistics(TasksManagement tasksManagement, ListView<String> statsList) {
        Map<Integer, Map<String, Integer>> stats = Utility.getTaskStatusStatistics(tasksManagement);
        ObservableList<String> items = FXCollections.observableArrayList();

        for (Employee employee : tasksManagement.getEmployees()) {
            Map<String, Integer> statMap = stats.get(employee.getIdEmployee());
            int completed = (statMap != null) ? statMap.getOrDefault("Completed", 0) : 0;
            int uncompleted = (statMap != null) ? statMap.getOrDefault("Uncompleted", 0) : 0;
            double workHours = tasksManagement.calculateEmployeeWorkDuration(employee);

            String s = employee.getIdEmployee() + ": " + employee.getName() +
                    " : Completed: " + completed +
                    ", Uncompleted: " + uncompleted +
                    ", Work Hours: " + workHours;
            items.add(s);
        }

        statsList.setItems(items);
    }
}
