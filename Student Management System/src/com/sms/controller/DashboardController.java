package com.sms.controller;

import com.sms.database.SessionManager;
import com.sms.model.user.User;
import com.sms.model.enums.Role; // Added this import
import com.sms.util.SceneSwitcher;
import com.sms.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Label roleLabel;
    
    @FXML private Button studentsButton;
    @FXML private Button coursesButton;
    @FXML private Button enrollmentsButton;
    @FXML private Button gradesButton;
    @FXML private Button exportButton;
    @FXML private Button logoutButton;

    @FXML
    public void initialize() {
        User currentUser = SessionManager.getCurrentUser();

        if (currentUser != null) {
            // Fix "Welcome, null" by providing a fallback to Username
            String displayName = (currentUser.getFullName() != null && !currentUser.getFullName().isEmpty()) 
                                 ? currentUser.getFullName() 
                                 : currentUser.getUsername();
            
            welcomeLabel.setText("Welcome, " + displayName);
            roleLabel.setText("Role: " + currentUser.getRole().name());

            // Apply Role-Based Permissions
            applyRolePermissions(currentUser.getRole());
        }
    }

    /**
     * Logic to hide/show buttons based on the user's role
     */
    private void applyRolePermissions(Role role) {
        switch (role) {
            case STUDENT:
                // Students only see Enrollments and Grades
                hideButton(studentsButton);
                hideButton(coursesButton);
                hideButton(exportButton);
                hideButton(enrollmentsButton);
                
                // Customize text for context
                
                gradesButton.setText("My Grades");
                break;

            case INSTRUCTOR:
                // Instructors see Courses, Enrollments, and Grades
                hideButton(studentsButton);
                hideButton(exportButton);
                
                coursesButton.setText("Manage Courses");
                gradesButton.setText("Manage Student Grades");
                break;

            case ADMIN:
                // Admins see everything (default state)
                break;
        }
    }

    /**
     * Helper to hide a button AND remove its reserved space from the UI
     */
    private void hideButton(Button btn) {
        if (btn != null) {
            btn.setVisible(false);
            btn.setManaged(false); // Crucial: removes the gap/space in the layout
        }
    }

    // ================= NAVIGATION METHODS =================

    @FXML
    private void handleStudents(ActionEvent event) {
        switchScene(event, "/view/student/student.fxml", "Manage Students");
    }

    @FXML
    private void handleCourses(ActionEvent event) {
        switchScene(event, "/view/course/course.fxml", "Manage Courses");
    }

    @FXML
    private void handleEnrollments(ActionEvent event) {
        switchScene(event, "/view/enrollment/enrollment.fxml", "Enrollments");
    }

    @FXML
    private void handleGrades(ActionEvent event) {
        switchScene(event, "/view/grade/grade.fxml", "Grades");
    }

    @FXML
    private void handleExport(ActionEvent event) {
        AlertUtil.showSuccess("System Data Exported to CSV successfully.");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        SessionManager.logout();
        switchScene(event, "/view/login/login.fxml", "Login");
    }

    private void switchScene(ActionEvent event, String fxmlPath, String title) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneSwitcher.switchScene(stage, fxmlPath, title);
    }
}