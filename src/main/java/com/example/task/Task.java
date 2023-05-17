package com.example.task;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {
    private final StringProperty taskDescription;

    public Task(String taskDescription) {
        this.taskDescription = new SimpleStringProperty(taskDescription);
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
}

