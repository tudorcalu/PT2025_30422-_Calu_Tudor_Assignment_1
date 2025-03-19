package com.example.gui;

import com.example.dataModel.ComplexTask;
import com.example.dataModel.SimpleTask;
import com.example.dataModel.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


public class ModifyTaskStatusDialog extends Dialog<Void> {

    public ModifyTaskStatusDialog(Task task) {
        setTitle("Modify Status: " + task.getName());

        if (task instanceof SimpleTask) {
            VBox contentBox = new VBox(10);
            contentBox.setPadding(new Insets(10));

            Label infoLabel = new Label("Select status for task: " + task.getName());
            ComboBox<String> statusCombo = new ComboBox<>();
            statusCombo.setItems(FXCollections.observableArrayList("Completed", "Uncompleted"));
            statusCombo.setValue(task.getStatus());
            contentBox.getChildren().addAll(infoLabel, statusCombo);

            getDialogPane().setContent(contentBox);
            getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    task.setStatus(statusCombo.getValue());
                }
                return null;
            });
        } else if (task instanceof ComplexTask) {
            VBox contentBox = new VBox(10);
            contentBox.setPadding(new Insets(10));

            Label infoLabel = new Label("Select a subtask of " + task.getName() + " to modify its status:");
            ListView<Task> subTaskList = new ListView<>();
            ObservableList<Task> subTasks = FXCollections.observableArrayList(((ComplexTask) task).getSubTasks());
            subTaskList.setItems(subTasks);
            subTaskList.setCellFactory(lv -> new ListCell<Task>() {
                @Override
                protected void updateItem(Task item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getIdTask() + ": " + item.getName() + " (" + item.getStatus() + ")");
                }
            });

            Button modifySubtaskButton = new Button("Modify Selected Subtask");
            modifySubtaskButton.setOnAction(e -> {
                Task selectedSubTask = subTaskList.getSelectionModel().getSelectedItem();
                if (selectedSubTask != null) {
                    ModifyTaskStatusDialog subTaskDialog = new ModifyTaskStatusDialog(selectedSubTask);
                    subTaskDialog.showAndWait();
                    ((ComplexTask) task).updateStatusBasedOnSubtasks();
                    subTaskList.refresh();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a subtask to modify.", ButtonType.OK);
                    alert.showAndWait();
                }
            });

            Button updateParentStatusButton = new Button("Update Complex Task Status");
            updateParentStatusButton.setOnAction(e -> {
                ((ComplexTask) task).updateStatusBasedOnSubtasks();
                Alert info = new Alert(Alert.AlertType.INFORMATION, "Complex Task status is now: " + task.getStatus(), ButtonType.OK);
                info.showAndWait();
            });

            contentBox.getChildren().addAll(infoLabel, subTaskList, modifySubtaskButton, updateParentStatusButton);
            getDialogPane().setContent(contentBox);
            getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        }
    }
}
