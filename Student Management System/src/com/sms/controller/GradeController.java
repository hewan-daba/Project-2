package com.sms.controller;

import com.sms.database.SessionManager;
import com.sms.model.user.User;
import com.sms.model.enums.Role;
import com.sms.model.academic.Enrollment;
import com.sms.model.academic.Grade;
import com.sms.model.enums.GradeType;
import com.sms.service.interfaces.EnrollmentService;
import com.sms.service.interfaces.GradeService;
import com.sms.service.impl.EnrollmentServiceImpl;
import com.sms.service.impl.GradeServiceImpl;
import com.sms.util.AlertUtil;
import com.sms.util.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GradeController {

    @FXML private VBox adminControlsBox; 
    @FXML private ComboBox<Enrollment> enrollmentComboBox;
    @FXML private ComboBox<GradeType> gradeTypeComboBox;
    @FXML private TextField scoreField;

    @FXML private TableView<Grade> gradeTable;
    @FXML private TableColumn<Grade, Integer> idColumn;
    @FXML private TableColumn<Grade, String> studentColumn;
    @FXML private TableColumn<Grade, String> courseColumn;
    @FXML private TableColumn<Grade, String> gradeTypeColumn;
    @FXML private TableColumn<Grade, Double> scoreColumn;

    private final GradeService gradeService = new GradeServiceImpl();
    private final EnrollmentService enrollmentService = new EnrollmentServiceImpl();
    private final ObservableList<Grade> gradeList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        User currentUser = SessionManager.getCurrentUser();

        // 1. Column Setup
        idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        studentColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEnrollment().getStudent().getFullName()));
        courseColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEnrollment().getCourse().getCourseName()));
        gradeTypeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGradeType().name()));
        scoreColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getScore()).asObject());

        // 2. Role-Based Logic
        if (currentUser.getRole() == Role.STUDENT) {
            // Hide admin features
            if (adminControlsBox != null) {
                adminControlsBox.setVisible(false);
                adminControlsBox.setManaged(false);
            }
            // Load only student's grades
            gradeList.setAll(gradeService.getGradesByStudentUsername(currentUser.getUsername()));
        } else {
            // Admin/Instructor View
            adminControlsBox.setVisible(true);
            adminControlsBox.setManaged(true);
            enrollmentComboBox.setItems(FXCollections.observableArrayList(enrollmentService.getAllEnrollments()));
            gradeTypeComboBox.setItems(FXCollections.observableArrayList(GradeType.values()));
            gradeList.setAll(gradeService.getAllGrades());
        }
        
        gradeTable.setItems(gradeList);
        refreshData();
    }

    @FXML
    private void handleAdd() {
        try {
            Enrollment enrollment = enrollmentComboBox.getValue();
            GradeType type = gradeTypeComboBox.getValue();
            double score = Double.parseDouble(scoreField.getText().trim());

            Grade grade = new Grade();
            grade.setEnrollment(enrollment);
            grade.setGradeType(type);
            grade.setScore(score);

            gradeService.addGrade(grade);
            AlertUtil.showSuccess("Grade added successfully.");
            handleClear();
            refreshData();
        } catch (Exception e) {
            AlertUtil.showError("Error", "Check your inputs: " + e.getMessage());
        }
    }

    private void refreshData() {
        User user = SessionManager.getCurrentUser();
        if (user.getRole() == Role.STUDENT) {
            gradeList.setAll(gradeService.getGradesByStudentUsername(user.getUsername()));
        } else {
            gradeList.setAll(gradeService.getAllGrades());
        }
    }

    @FXML
    private void handleDelete() {
        Grade selected = gradeTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            gradeService.deleteGrade(selected.getId());
            refreshData();
        }
    }

    @FXML private void handleClear() {
        enrollmentComboBox.setValue(null);
        gradeTypeComboBox.setValue(null);
        scoreField.clear();
    }

    @FXML private void handleBack() {
        Stage stage = (Stage) gradeTable.getScene().getWindow();
        SceneSwitcher.switchScene(stage, "/view/dashboard/dashboard.fxml", "Dashboard");
    }
    
    @FXML private void handleUpdate() { AlertUtil.showInfo("Update", "Feature coming soon."); }
}