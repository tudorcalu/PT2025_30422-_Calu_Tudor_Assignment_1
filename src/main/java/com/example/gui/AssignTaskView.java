package com.example.gui;

import com.example.dataModel.Employee;
import com.example.dataModel.Task;
import com.example.businessLogic.TasksManagement;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AssignTaskView extends VBox {

    public AssignTaskView(TasksManagement tasksManagement) {
        setSpacing(10);
        setPadding(new Insets(10));

        Label titleLabel = new Label("Assign Task to Employee");

        ComboBox<Employee> employeeCombo = new ComboBox<>();
        employeeCombo.setItems(tasksManagement.getEmployees());
        employeeCombo.setCellFactory(lv -> new javafx.scene.control.ListCell<Employee>() {
            @Override
            protected void updateItem(Employee employee, boolean empty) {
                super.updateItem(employee, empty);
                setText(empty || employee == null ? null : employee.getName());
            }
        });
        employeeCombo.setButtonCell(employeeCombo.getCellFactory().call(null));

        ComboBox<Task> taskCombo = new ComboBox<>();
        taskCombo.setItems(tasksManagement.getUnassignedTasks());
        taskCombo.setCellFactory(lv -> new javafx.scene.control.ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                setText(empty || task == null ? null : task.getName());
            }
        });
        taskCombo.setButtonCell(taskCombo.getCellFactory().call(null));

        Button assignButton = new Button("Assign Task");
        Label messageLabel = new Label();

        assignButton.setOnAction(e -> {
            Employee selectedEmployee = employeeCombo.getValue();
            Task selectedTask = taskCombo.getValue();

            if (selectedEmployee == null || selectedTask == null) {
                messageLabel.setText("Please select both an employee and a task.");
                return;
            }
            tasksManagement.assignTaskToEmployee(selectedEmployee, selectedTask);
            tasksManagement.removeUnassignedTask(selectedTask);
            messageLabel.setText("Task '" + selectedTask.getName()
                    + "' assigned to " + selectedEmployee.getName() + ".");
        });

        getChildren().addAll(titleLabel, new Label("Select Employee:"), employeeCombo,
                new Label("Select Task:"), taskCombo, assignButton, messageLabel);
    }
}
