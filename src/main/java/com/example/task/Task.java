package com.example.task;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {
    private final StringProperty taskDescription;
    private int id;
    public Task(String taskDescription) {
        this.taskDescription = new SimpleStringProperty(taskDescription);
        this.id = -1;
    }
    public Task(String taskDescription, int id) {
        this.taskDescription = new SimpleStringProperty(taskDescription);
        this.id = id;
    }

    public String getTaskDescription() {
        return taskDescription.get();
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription.set(taskDescription);
    }

    public StringProperty taskDescriptionProperty() {
        return taskDescription;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}

