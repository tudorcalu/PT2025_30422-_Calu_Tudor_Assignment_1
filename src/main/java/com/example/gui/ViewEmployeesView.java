package com.example.gui;

import com.example.dataModel.Employee;
import com.example.dataModel.Task;
import com.example.businessLogic.TasksManagement;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class ViewEmployeesView extends VBox {

    public ViewEmployeesView(TasksManagement tasksManagement) {
        setSpacing(10);
        setPadding(new Insets(10));

        Label titleLabel = new Label("View Employees and Their Tasks");
        ListView<Employee> employeeListView = new ListView<>();
        ObservableList<Employee> employees = tasksManagement.getEmployees();
        employeeListView.setItems(employees);

        employeeListView.setCellFactory(lv -> new ListCell<Employee>() {
            @Override
            protected void updateItem(Employee employee, boolean empty) {
                super.updateItem(employee, empty);
                if (empty || employee == null) {
                    setText(null);
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(employee.getName()).append(" - Tasks: ");
                    for (Task task : employee.getTasks()) {
                        sb.append(task.getName())
                                .append(" (").append(task.getStatus()).append(") ");
                    }
                    setText(sb.toString());
                }
            }
        });

        getChildren().addAll(titleLabel, employeeListView);
    }
}
