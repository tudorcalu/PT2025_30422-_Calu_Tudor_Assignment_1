package com.example.gui;

import com.example.dataModel.SimpleTask;
import com.example.dataModel.Task;
import com.example.dataModel.ComplexTask;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class SubTaskDialog extends Dialog<Task> {
    private TextField taskNameField;
    private TextField startHourField;
    private TextField endHourField;
    private ComboBox<String> taskTypeCombo;

    public SubTaskDialog() {
        setTitle("Add Subtask");

        taskNameField = new TextField();
        taskNameField.setPromptText("Subtask Name");

        startHourField = new TextField();
        startHourField.setPromptText("Start Hour (for Simple Task)");

        endHourField = new TextField();
        endHourField.setPromptText("End Hour (for Simple Task)");

        taskTypeCombo = new ComboBox<>();
        taskTypeCombo.setItems(FXCollections.observableArrayList("Simple", "Complex"));
        taskTypeCombo.setValue("Simple");


        taskTypeCombo.setOnAction(e -> {
            boolean isComplex = "Complex".equals(taskTypeCombo.getValue());
            startHourField.setDisable(isComplex);
            endHourField.setDisable(isComplex);
        });

        VBox content = new VBox(10,
                new Label("Subtask Name:"), taskNameField,
                new Label("Task Type:"), taskTypeCombo,
                new Label("Start Hour (for Simple Task):"), startHourField,
                new Label("End Hour (for Simple Task):"), endHourField);
        content.setPadding(new Insets(10));
        getDialogPane().setContent(content);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                String name = taskNameField.getText();
                if ("Simple".equals(taskTypeCombo.getValue())) {
                    try {
                        double startHour = Double.parseDouble(startHourField.getText());
                        double endHour = Double.parseDouble(endHourField.getText());
                        return new SimpleTask(name, startHour, endHour);
                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid start or end hour.", ButtonType.OK);
                        alert.showAndWait();
                        return null;
                    }
                } else {

                    ComplexTaskBuilderDialog builderDialog = new ComplexTaskBuilderDialog();
                    builderDialog.taskNameField.setText(name);
                    return builderDialog.showAndWait().orElse(null);
                }
            }
            return null;
        });
    }
}
