package com.example.task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private MainProjectController mainProjectController;
    public static MainController instance;
    private String currentUsername;
    private Stage primaryStage;
    private Scene loginScene;
    private Scene authorizationScene;
    private Scene projectScene;

    public MainController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        instance = this;
        System.out.println("MainController instance created");
    }

    public void loadScenes() throws Exception {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("login-app.fxml"));
        Parent loginRoot = loginLoader.load();
        LoginController loginController = loginLoader.getController();
        loginController.setMainController(this);
        loginScene = new Scene(loginRoot);

        FXMLLoader authorizationLoader = new FXMLLoader(getClass().getResource("authorization-app.fxml"));
        Parent authorizationRoot = authorizationLoader.load();
        AuthorizationController authorizationController = authorizationLoader.getController();
        authorizationController.setMainController(this);
        authorizationScene = new Scene(authorizationRoot);

        FXMLLoader projectLoader = new FXMLLoader(getClass().getResource("main-project.fxml"));
        Parent mainRoot = projectLoader.load();
        mainProjectController = projectLoader.getController();
        mainProjectController.setMainController(this);
        projectScene = new Scene(mainRoot);

    }

    public void showLoginScene() throws IOException {
        primaryStage.setScene(loginScene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.isAlwaysOnTop();
        primaryStage.setTitle("Project manager");
        primaryStage.getIcons().add(new Image(getClass().getResource("icon.png").openStream()));

    }

    public void showAuthorizationScene() {
        primaryStage.setScene(authorizationScene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.isAlwaysOnTop();
        primaryStage.setTitle("Project manager");

    }
    public void showProjectScene() {
        primaryStage.setScene(projectScene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.alwaysOnTopProperty();
        primaryStage.setTitle("Project manager");
    }
    public MainProjectController getMainProjectController() {
        return mainProjectController;
    }
}
