package com.example.gui;

import com.example.businessLogic.TasksManagement;
import com.example.dataModel.SimpleTask;
import com.example.dataModel.Task;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class AddTaskView extends VBox {

    public AddTaskView(TasksManagement tasksManagement) {
        setSpacing(10);
        setPadding(new Insets(10));

        Label titleLabel = new Label("Add Task");
        Label typeLabel = new Label("Select Task Type:");
        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Simple", "Complex");
        typeCombo.setValue("Simple");


        Label nameLabel = new Label("Task Name:");
        TextField nameField = new TextField();

        Label startLabel = new Label("Start Hour (for Simple Task):");
        TextField startField = new TextField();
        Label endLabel = new Label("End Hour (for Simple Task):");
        TextField endField = new TextField();


        Button createComplexTaskButton = new Button("Create Complex Task");
        createComplexTaskButton.setVisible(false); // Only visible if Complex is selected

        Button addButton = new Button("Add Task");
        Label messageLabel = new Label();

        typeCombo.setOnAction(e -> {
            String selectedType = typeCombo.getValue();
            if ("Complex".equals(selectedType)) {

                nameField.setDisable(true);
                startField.setDisable(true);
                endField.setDisable(true);
                createComplexTaskButton.setVisible(true);
            } else {

                nameField.setDisable(false);
                startField.setDisable(false);
                endField.setDisable(false);
                createComplexTaskButton.setVisible(false);
            }
        });

        createComplexTaskButton.setOnAction(e -> {

            ComplexTaskBuilderDialog dialog = new ComplexTaskBuilderDialog();
            dialog.setTitle("Create Complex Task");
            dialog.showAndWait().ifPresent(complexTask -> {

                tasksManagement.addUnassignedTask(complexTask);
                messageLabel.setText("Complex Task '" + complexTask.getName() + "' added.");
            });
        });


        addButton.setOnAction(e -> {
            if ("Simple".equals(typeCombo.getValue())) {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    messageLabel.setText("Please enter task name.");
                    return;
                }
                try {
                    double start = Double.parseDouble(startField.getText().trim());
                    double end = Double.parseDouble(endField.getText().trim());
                    Task task = new SimpleTask(name, start, end);
                    tasksManagement.addUnassignedTask(task);
                    messageLabel.setText("Simple Task added.");
                } catch (NumberFormatException ex) {
                    messageLabel.setText("Invalid start or end hour.");
                }
            }
        });

        getChildren().addAll(
                titleLabel,
                typeLabel,
                typeCombo,
                nameLabel,
                nameField,
                startLabel,
                startField,
                endLabel,
                endField,
                createComplexTaskButton,
                addButton,
                messageLabel
        );
    }
}
