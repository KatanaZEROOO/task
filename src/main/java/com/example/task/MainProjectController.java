package com.example.task;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private Button addButton3;

    @FXML
    private Button deleteButton1;

    @FXML
    private Button deleteButton2;

    @FXML
    private Button deleteButton3;

    @FXML
    private TableView<Task> myWorkTableView;

    @FXML
    private TableColumn<Task, String> tableColumn;

    @FXML
    private TextField myWorkTextField;

    @FXML
    private TextField projectTextField;

    @FXML
    private TextField updateTextField;

    @FXML
    void initialize() {
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("taskDescription"));
        addButton1.setOnAction(event -> addTask());
        deleteButton1.setOnAction(event -> deleteTask());
    }
    public void addTask() {
        String taskDescription = myWorkTextField.getText().trim();
        String user = Users.getCurrentUser();
        if (!taskDescription.isEmpty()) {
            Task task = new Task(taskDescription);
            myWorkTableView.getItems().add(task);
            myWorkTextField.clear();
            try {
                DatabaseManager.insertTask(task, user);
            } catch (SQLException e) {
                System.out.println("Failed to add task: " + e.getMessage());
            }
        }
    }
    public void deleteTask() {
        Task selectedTask = myWorkTableView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            myWorkTableView.getItems().remove(selectedTask);
            myWorkTableView.setEditable(true);
            try {
                DatabaseManager.deleteTask(selectedTask);
            } catch (SQLException e) {
                System.out.println("Failed to delete task: " + e.getMessage());
            }
        }
    }
    public void loadData(String username) {
        try {
            DatabaseManager.createUsersTable();
            List<Task> tasks = DatabaseManager.getTasks(username);
            myWorkTableView.getItems().addAll(tasks);
            System.out.println("Added tasks to table view");
        } catch (SQLException e) {
            System.out.println("Failed to initialize tasks: " + e.getMessage());
        }
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
