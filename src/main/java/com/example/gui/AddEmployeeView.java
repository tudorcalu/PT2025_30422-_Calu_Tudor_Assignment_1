package com.example.gui;

import com.example.dataModel.Employee;
import com.example.businessLogic.TasksManagement;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AddEmployeeView extends VBox {

    public AddEmployeeView(TasksManagement tasksManagement) {
        setSpacing(10);
        setPadding(new Insets(10));

        Label titleLabel = new Label("Add Employee");
        Label nameLabel = new Label("Employee Name:");
        TextField nameField = new TextField();
        Button addButton = new Button("Add Employee");
        Label messageLabel = new Label();

        addButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                Employee employee = new Employee(name);
                tasksManagement.addEmployee(employee);
                messageLabel.setText("Employee added successfully!");
                nameField.clear();
            } else {
                messageLabel.setText("Please enter a name.");
            }
        });

        getChildren().addAll(titleLabel, nameLabel, nameField, addButton, messageLabel);
    }
}
