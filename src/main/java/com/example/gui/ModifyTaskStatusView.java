package com.example.gui;

import com.example.businessLogic.TasksManagement;
import com.example.dataModel.ComplexTask;
import com.example.dataModel.Employee;
import com.example.dataModel.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ModifyTaskStatusView extends VBox {

    public ModifyTaskStatusView(TasksManagement tasksManagement) {
        setSpacing(10);
        setPadding(new Insets(10));

        Label titleLabel = new Label("Modify Task Status");

        ComboBox<Employee> employeeCombo = new ComboBox<>();
        employeeCombo.setItems(tasksManagement.getEmployees());
        employeeCombo.setPromptText("Select Employee");
        employeeCombo.setCellFactory(lv -> new ListCell<Employee>() {
            @Override
            protected void updateItem(Employee employee, boolean empty) {
                super.updateItem(employee, empty);
                setText(empty || employee == null ? null : employee.getName());
            }
        });
        employeeCombo.setButtonCell(employeeCombo.getCellFactory().call(null));

        ComboBox<Task> taskCombo = new ComboBox<>();
        taskCombo.setPromptText("Select Task");
        taskCombo.setCellFactory(lv -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                setText(empty || task == null ? null : task.getName() + " (" + task.getStatus() + ")");
            }
        });
        taskCombo.setButtonCell(taskCombo.getCellFactory().call(null));

        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.setItems(FXCollections.observableArrayList("Completed", "Uncompleted"));
        statusCombo.setPromptText("Select New Status");

        employeeCombo.setOnAction(e -> {
            Employee selectedEmployee = employeeCombo.getValue();
            if (selectedEmployee != null) {
                ObservableList<Task> tasks = FXCollections.observableArrayList(selectedEmployee.getTasks());
                taskCombo.setItems(tasks);
            } else {
                taskCombo.getItems().clear();
            }
        });

        Button updateButton = new Button("Update Task Status");
        Label messageLabel = new Label();

        updateButton.setOnAction(e -> {
            Employee selectedEmployee = employeeCombo.getValue();
            Task selectedTask = taskCombo.getValue();

            if (selectedEmployee == null || selectedTask == null) {
                messageLabel.setText("Please select an employee and a task.");
                return;
            }

            if (selectedTask instanceof ComplexTask) {
                ModifyTaskStatusDialog dialog = new ModifyTaskStatusDialog(selectedTask);
                dialog.showAndWait();
                messageLabel.setText("Complex task status updated (check nested dialogs for details).");
            } else {
                String newStatus = statusCombo.getValue();
                if (newStatus == null) {
                    messageLabel.setText("Please select a new status for the simple task.");
                    return;
                }
                selectedTask.setStatus(newStatus);
                messageLabel.setText("Task '" + selectedTask.getName() + "' updated to " + newStatus + ".");
            }
        });

        getChildren().addAll(
                titleLabel,
                new Label("Select Employee:"), employeeCombo,
                new Label("Select Task:"), taskCombo,
                new Label("Select New Status (for Simple Tasks):"), statusCombo,
                updateButton,
                messageLabel
        );
    }
}
