package com.example.task;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProjectWindowController {

    private MainController mainController;

    private Projects currentProject;

    @FXML
    private Button addUser;

    @FXML
    private Button saveButton;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private DatePicker dataPicker;

    @FXML
    private DatePicker endDataPicker;

    @FXML
    private TextArea descriptionProject;

    @FXML
    private ListView<String> whoWorkListView;

    @FXML
    private ComboBox<String> priorityComboBox;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private ComboBox<String> whoWorkComboBox;

    @FXML
    private TextField projectNameField;

    @FXML
    private ListView<?> projectResources;

    @FXML
    void initialize() {
        backButton.setOnAction(event -> onBackButtonClicked());
        populateComboBox();
        saveButton.setOnAction(event -> onSaveButtonClicked());
        addUser.setOnAction(event -> onAddUserButtonClicked());

    }
    public void onSaveButtonClicked() {
        int projectId = currentProject.getId();
        String projectName = projectNameField.getText();
        String projectDescription = descriptionProject.getText();

        LocalDate startDateLocal = dataPicker.getValue();
        String startDate = startDateLocal != null ? startDateLocal.toString() : "";

        LocalDate endDateLocal = endDataPicker.getValue();
        String endDate = endDateLocal != null ? endDateLocal.toString() : "";

        List<String> assignedTo = new ArrayList<>();
        if (whoWorkListView.getSelectionModel().getSelectedItems() != null) {
            for (Object item : whoWorkListView.getSelectionModel().getSelectedItems()) {
                assignedTo.add(item.toString());
            }
        }

        Object priorityObject = priorityComboBox.getValue();
        String priority = priorityObject != null ? priorityObject.toString() : "";

        String createdBy = Users.getCurrentUser();

        Object statusObject = statusComboBox.getValue();
        String status = statusObject != null ? statusObject.toString() : "";


        Projects newProject = new Projects(projectId, projectName, projectDescription, startDate, endDate, assignedTo, createdBy, status, priority);

        if (!createdBy.isEmpty()) {
            try {
                int newProjectId = DatabaseManager.insertProject(newProject, assignedTo);
                newProject.setId(newProjectId);
                currentProject = newProject;
                mainController.showWindowProject();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to create project: 'createdBy' is empty");
        }
    }
    public void  populateComboBox() {
        List<String> priorities = Arrays.asList("Неважно", "Средняя", "Высокая", "Срочная");
        List<String> status = Arrays.asList("Не завершен", "В работе", "Завершен");

        List<String> users = new ArrayList<>();
        try {
            users = DatabaseManager.getAllUsers();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve users: " + e.getMessage());
        }
        statusComboBox.getItems().addAll(status);
        priorityComboBox.getItems().addAll(priorities);
        whoWorkComboBox.getItems().addAll(users);
    }
    public void onAddUserButtonClicked() {
        String selectedUser = whoWorkComboBox.getSelectionModel().getSelectedItem();
        if (selectedUser != null && !whoWorkListView.getItems().contains(selectedUser)) {
            whoWorkListView.getItems().add(selectedUser);
        }
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setProject(Projects project) {
        this.currentProject = project;
    }

    public void onBackButtonClicked() {
        mainController.showProjectScene();
    }
}