package com.example.task;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class Projects {

    private StringProperty projectName;
    private StringProperty projectDescription;
    private StringProperty startDate;
    private StringProperty endDate;
    private ListProperty<String> assignedTo;
    private StringProperty createdBy;
    private StringProperty status;
    private StringProperty priority;
    private int id;

    public Projects(int id, String projectName, String projectDescription, String startDate, String endDate, List<String> assignedTo, String createdBy, String status, String priority) {
        this.id = id;
        this.projectName = new SimpleStringProperty(projectName);
        this.projectDescription = new SimpleStringProperty(projectDescription);
        this.startDate = new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
        this.assignedTo = new SimpleListProperty<>(FXCollections.observableArrayList(assignedTo));
        this.createdBy = new SimpleStringProperty(createdBy);
        this.status = new SimpleStringProperty(status);
        this.priority = new SimpleStringProperty(priority);
    }

    public Projects() {
        this.projectName = new SimpleStringProperty("");
        this.assignedTo = new SimpleListProperty<>(FXCollections.observableArrayList());
    }


    public String getProjectName() {
        return projectName.get();
    }

    public StringProperty projectNameProperty() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName.set(projectName);
    }

    public String getProjectDescription() {
        return projectDescription.get();
    }

    public StringProperty projectDescriptionProperty() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription.set(projectDescription);
    }

    public String getStartDate() {
        return startDate.get();
    }

    public StringProperty startDateProperty() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public String getEndDate() {
        return endDate.get();
    }

    public StringProperty endDateProperty() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate.set(endDate);
    }

    public ObservableList<String> getAssignedTo() {
        return assignedTo.get();
    }

    public ListProperty<String> assignedToProperty() {
        return assignedTo;
    }

    public void setAssignedTo(ObservableList<String> assignedTo) {
        this.assignedTo.set(assignedTo);
    }

    public String getCreatedBy() {
        return createdBy.get();
    }

    public StringProperty createdByProperty() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy.set(createdBy);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getPriority() {
        return priority.get();
    }

    public StringProperty priorityProperty() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}