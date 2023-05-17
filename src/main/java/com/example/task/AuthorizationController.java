package com.example.task;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthorizationController {
    private MainController mainController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private PasswordField registerPasswordField;

    @FXML
    private TextField registerUsernameField;

    @FXML
    private Button signInButton;

    @FXML
    void initialize() {
        backButton.setOnAction(event -> {
            try {
                onBackButtonClicked();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        signInButton.setOnAction(event -> {
            try {
                registerButtonClicked();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void onBackButtonClicked() throws IOException {
        mainController.showLoginScene();
    }

    public void registerButtonClicked() throws SQLException {
        String username = registerUsernameField.getText();
        String password = registerPasswordField.getText();

        Users user = new Users(username, password);
        DatabaseManager.insertUser(user);
    }
}
