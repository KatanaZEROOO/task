package com.example.task;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    private MainController mainController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private CheckBox rememberMeBox;

    @FXML
    private TextField usernameField;

    @FXML
    void initialize() {
        registerButton.setOnAction(event -> onAuthorizationButtonClicked());
        loginButton.setOnAction(event -> loginButtonAction());
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void onAuthorizationButtonClicked() {
        mainController.showAuthorizationScene();
    }

    public void loginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (DatabaseManager.validateUser(username, password)) {
            Users.setCurrentUser(username);
            mainController.showProjectScene();
            MainProjectController mainProjectController = mainController.getMainProjectController();
            mainProjectController.loadData(username);
        } else {
            System.out.println("login fail");
            // Login failed, show an error message
        }
    }
}