package com.example.gui;

import com.example.dataModel.ComplexTask;
import com.example.dataModel.Task;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ComplexTaskBuilderDialog extends Dialog<ComplexTask> {

    public TextField taskNameField;
    private TreeView<Task> subTaskTree;
    private Button addSubtaskButton;
    private ComplexTask complexTask;

    public ComplexTaskBuilderDialog() {
        setTitle("Create Complex Task");

        taskNameField = new TextField();
        taskNameField.setPromptText("Enter complex task name");

        subTaskTree = new TreeView<>();
        complexTask = new ComplexTask("New Complex Task"); // Placeholder; updated on OK.
        TreeItem<Task> rootItem = new TreeItem<>(complexTask);
        rootItem.setExpanded(true);
        subTaskTree.setRoot(rootItem);

        addSubtaskButton = new Button("Add Subtask");
        addSubtaskButton.setOnAction(e -> {
            SubTaskDialog subTaskDialog = new SubTaskDialog();
            subTaskDialog.setTitle("Add Subtask");
            subTaskDialog.showAndWait().ifPresent(task -> {
                complexTask.addSubTask(task);
                TreeItem<Task> newItem = new TreeItem<>(task);
                rootItem.getChildren().add(newItem);
            });
        });

        VBox content = new VBox(10, new Label("Complex Task Name:"), taskNameField,
                subTaskTree, addSubtaskButton);
        content.setPadding(new Insets(10));
        getDialogPane().setContent(content);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                String name = taskNameField.getText();
                complexTask.setName(name);
                return complexTask;
            }
            return null;
        });
    }
}
