package com.example.task;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class MainProjectController {

    private MainController mainController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton1;

    @FXML
    private Button addButton2;

    @FXML
    private Button deleteButton1;

    @FXML
    private Button deleteButton2;

    @FXML
    private Button createProjectButton;

    @FXML
    private Button deleteButton3;

    @FXML
    private Button openButton;

    @FXML
    private TableView<Task> myWorkTableView;

    @FXML
    private TableColumn<Task, String> tableColumn;

    @FXML
    private TableColumn<Projects, String> projectColumn;

    @FXML
    private TableView<Projects> projectTable;

    @FXML
    private TextField myWorkTextField;

    @FXML
    private TextField updateTextField;

    @FXML
    void initialize() {
        tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("TaskDescription"));

        projectColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("projectDescription"));


        openButton.setOnAction(event -> onOpenButtonClicked());

        deleteButton3.setOnAction(event -> deleteProject());
        createProjectButton.setOnAction(event -> onCreateProjectButtonClicked());

        myWorkTextField.setOnAction(event -> addTask());
        addButton1.setOnAction(event -> addTask());
        deleteButton1.setOnAction(event -> deleteTask());


        tableColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task, String> event) {
                        Task task = event.getTableView().getItems().get(event.getTablePosition().getRow());
                        task.setTaskDescription(event.getNewValue());
                        if (event.getNewValue().isEmpty()) {
                            try {
                                DatabaseManager.deleteTask(task);
                                myWorkTableView.getItems().remove(task);
                            } catch (SQLException e) {
                                System.out.println("Failed delete task: " + e.getMessage());
                            }
                        } else {
                            task.setTaskDescription(event.getNewValue());
                            try {
                                DatabaseManager.updateTask(task);
                            } catch (SQLException e) {
                                System.out.println("Failed to update task: " + e.getMessage());
                            }
                        }
                    }
                }
        );
    }
    public void onOpenButtonClicked() {
        Projects selectedProject = projectTable.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            mainController.showProjectDetail(selectedProject);
        } else {
            System.out.println("No project selected.");
        }
    }
    public void addTask() {
        String taskDescription = myWorkTextField.getText().trim();
        String user = Users.getCurrentUser();
        if (!taskDescription.isEmpty()) {
            Task task = new Task(taskDescription);
            myWorkTableView.getItems().add(task);
            myWorkTextField.clear();
            try {
                int taskId = DatabaseManager.insertTask(task, user);
                task.setId(taskId);
            } catch (SQLException e) {
                System.out.println("Failed to add task: " + e.getMessage());
            }
        }
    }

    public void deleteTask() {
        Task selectedTask = myWorkTableView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            myWorkTableView.getItems().remove(selectedTask);
            try {
                DatabaseManager.deleteTask(selectedTask);
            } catch (SQLException e) {
                System.out.println("Failed to delete task: " + e.getMessage());
            }
        }
    }

    public void loadData(String username) {
        try {
            List<Task> tasks = DatabaseManager.getTasks(username);
            myWorkTableView.getItems().addAll(tasks);
            System.out.println("Added tasks to table view");
        } catch (SQLException e) {
            System.out.println("Failed to initialize tasks: " + e.getMessage());
        }
    }

    public void onCreateProjectButtonClicked() {
        mainController.showWindowProject();
    }
    public void deleteProject() {
        Projects selectedProject = projectTable.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            projectTable.getItems().remove(selectedProject);
            try {
                DatabaseManager.deleteProject(selectedProject);
            } catch (SQLException e) {
                System.out.println("Failed to delete project: " + e.getMessage());
            }
        }
    }

    public void loadProject() {
        try {
            List<Projects> projects = DatabaseManager.getProjects();
            for (Projects project : projects) {
                projectTable.getItems().add(project);
            }
            System.out.println("Added project names to table view");
        } catch (SQLException e) {
            System.out.println("Failed to initialize projects: " + e.getMessage());
        }
    }



    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
