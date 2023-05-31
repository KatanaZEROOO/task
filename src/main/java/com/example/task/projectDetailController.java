package com.example.task;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class projectDetailController {
    private MainController mainController;
    private Projects currentProject;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField assignTaskTextFiled;

    @FXML
    private ListView<String> assignedToListView;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button sendButton;

    @FXML
    private DatePicker endDate;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<String> priorityComboBox;

    @FXML
    private TextField projectName;

    @FXML
    private DatePicker startDate;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    void initialize() {
        saveButton.setOnAction(event -> onSaveButtonClicked());
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void onSaveButtonClicked() {
        mainController.showProjectScene();
    }

    public void setProject(Projects project) {
        this.currentProject = project;
        projectName.setText(project.getProjectName());
        descriptionTextArea.setText(project.getProjectDescription());
        startDate.setValue(LocalDate.parse(project.getStartDate()));
        endDate.setValue(LocalDate.parse(project.getEndDate()));
        assignedToListView.getItems().addAll(project.getAssignedTo());
        priorityComboBox.setValue(project.getPriority());
        statusComboBox.setValue(project.getStatus());
    }
}
