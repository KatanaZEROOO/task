package com.example.task;

import javafx.application.Application;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseManager.createProjectUsersTable();
        DatabaseManager.createUsersTable();
        DatabaseManager.createProjectsTable();
        DatabaseManager.createTasksTable();
        MainController mainController = new MainController(primaryStage);
        mainController.loadScenes();
        mainController.showLoginScene();
    }

    public static void main(String[] args) {
        launch();
    }
}