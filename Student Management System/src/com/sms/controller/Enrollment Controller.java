package com.sms.controller;

import com.sms.model.academic.Course;
import com.sms.model.academic.Enrollment;
import com.sms.model.academic.Student;
import com.sms.model.enums.EnrollmentStatus;
import com.sms.service.interfaces.CourseService;
import com.sms.service.interfaces.EnrollmentService;
import com.sms.service.interfaces.StudentService;
import com.sms.service.impl.CourseServiceImpl;
import com.sms.service.impl.EnrollmentServiceImpl;
import com.sms.service.impl.StudentServiceImpl;
import com.sms.util.AlertUtil;
import com.sms.util.SceneSwitcher;
import com.sms.dao.impl.EnrollmentDAOImpl;
import com.sms.dao.impl.StudentDAOImpl;
import com.sms.dao.impl.CourseDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class EnrollmentController {

    @FXML private ComboBox<Student> studentComboBox;
    @FXML private ComboBox<Course> courseComboBox;
    @FXML private ComboBox<EnrollmentStatus> statusComboBox;

    @FXML private TableView<Enrollment> enrollmentTable;
    @FXML private TableColumn<Enrollment, Integer> idColumn;
    @FXML private TableColumn<Enrollment, String> studentColumn;
    @FXML private TableColumn<Enrollment, String> courseColumn;
    @FXML private TableColumn<Enrollment, String> statusColumn;

    private final StudentService studentService = new StudentServiceImpl();
    private final CourseService courseService = new CourseServiceImpl();
    
    // Initializing Service with DAOs
    private final EnrollmentService enrollmentService = new EnrollmentServiceImpl(
            new EnrollmentDAOImpl(), new StudentDAOImpl(), new CourseDAOImpl());

    private final ObservableList<Enrollment> enrollmentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Load dropdown data
        studentComboBox.setItems(FXCollections.observableArrayList(studentService.getAllStudents()));
        courseComboBox.setItems(FXCollections.observableArrayList(courseService.getAllCourses()));
        statusComboBox.setItems(FXCollections.observableArrayList(EnrollmentStatus.values()));

        // Table column mapping
        idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        
        studentColumn.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getStudent() != null ? 
                    data.getValue().getStudent().getFullName() : "N/A"));

        courseColumn.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getCourse() != null ? 
                    data.getValue().getCourse().getCourseName() : "N/A"));

        statusColumn.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getStatus().name()));

        loadEnrollments();
    }

    @FXML
    private void handleAdd() {
        try {
            Student student = studentComboBox.getValue();
            Course course = courseComboBox.getValue();
            EnrollmentStatus status = statusComboBox.getValue();

            if (student == null || course == null || status == null) {
                AlertUtil.showError("Error", "All fields must be selected.");
                return;
            }

            Enrollment enrollment = new Enrollment();
            enrollment.setStudentId(student.getId());
            enrollment.setCourseId(course.getId());
            enrollment.setStatus(status);

            enrollmentService.addEnrollment(enrollment);
            AlertUtil.showSuccess("Student enrolled successfully.");
            handleClear();
            loadEnrollments();
        } catch (Exception e) {
            AlertUtil.showError("Error", e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        AlertUtil.showSuccess("Update feature coming soon.");
    }

    @FXML
    private void handleDelete() {
        Enrollment selected = enrollmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.showError("Select Error", "Select an enrollment to delete.");
            return;
        }

        try {
            enrollmentService.deleteEnrollment(selected.getId());
            AlertUtil.showSuccess("Enrollment removed successfully.");
            loadEnrollments();
        } catch (Exception e) {
            AlertUtil.showError("Error", e.getMessage());
        }
    }

    @FXML
    private void handleClear() {
        studentComboBox.setValue(null);
        courseComboBox.setValue(null);
        statusComboBox.setValue(null);
        enrollmentTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleBack() {
        Stage stage = (Stage) enrollmentTable.getScene().getWindow();
        SceneSwitcher.switchScene(stage, "/view/dashboard/dashboard.fxml", "Dashboard");
    }

    private void loadEnrollments() {
        enrollmentList.setAll(enrollmentService.getAllEnrollments());
        enrollmentTable.setItems(enrollmentList);
    }
}