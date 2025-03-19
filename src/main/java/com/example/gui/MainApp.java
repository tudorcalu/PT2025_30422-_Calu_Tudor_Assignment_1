package com.example.gui;

import com.example.businessLogic.TasksManagement;
import com.example.dataAccess.DataPersistence;
import com.example.dataModel.Employee;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    private final TasksManagement tasksManagement = DataPersistence.loadData();
    private BorderPane root;

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();

        VBox navPane = new VBox(10);
        navPane.setPadding(new Insets(10));
        Button addEmployeeBtn = new Button("Add Employee");
        Button addTaskBtn = new Button("Add Task");
        Button assignTaskBtn = new Button("Assign Task");
        Button viewEmployeesBtn = new Button("View Employees");
        Button viewStatsBtn = new Button("View Statistics");
        Button modifyTaskBtn = new Button("Modify Task");

        addEmployeeBtn.setOnAction(e -> setCenter(new AddEmployeeView(tasksManagement)));
        addTaskBtn.setOnAction(e -> setCenter(new AddTaskView(tasksManagement)));
        assignTaskBtn.setOnAction(e -> setCenter(new AssignTaskView(tasksManagement)));
        viewEmployeesBtn.setOnAction(e -> setCenter(new ViewEmployeesView(tasksManagement)));
        viewStatsBtn.setOnAction(e -> setCenter(new ViewStatisticsView(tasksManagement)));
        modifyTaskBtn.setOnAction(e -> setCenter(new ModifyTaskStatusView(tasksManagement)));

        navPane.getChildren().addAll(addEmployeeBtn, addTaskBtn, assignTaskBtn, viewEmployeesBtn, viewStatsBtn, modifyTaskBtn);
        root.setLeft(navPane);

        setCenter(new AddEmployeeView(tasksManagement));

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Task Management App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setCenter(javafx.scene.layout.Pane pane) {
        root.setCenter(pane);
    }

    @Override
    public void stop() {
        DataPersistence.saveData(tasksManagement);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
