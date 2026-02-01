package com.sms.dao.impl;

import com.sms.dao.interfaces.GradeDAO;
import com.sms.database.DatabaseConnection;
import com.sms.exception.DataAccessException;
import com.sms.model.academic.*;
import com.sms.model.enums.GradeType;
import com.sms.model.enums.EnrollmentStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAOImpl implements GradeDAO {

    @Override
    public List<Grade> getAllGrades() {
        String sql = "SELECT g.*, s.full_name, s.student_id, c.course_name " +
                     "FROM grades g " +
                     "JOIN enrollments e ON g.enrollment_id = e.id " +
                     "JOIN students s ON e.student_id = s.id " +
                     "JOIN courses c ON e.course_id = c.id";
        return fetchGradesWithSql(sql, null);
    }

    @Override
    public List<Grade> getGradesByStudentUsername(String username) {
        // This query filters specifically for the logged-in student's ID
        String sql = "SELECT g.*, s.full_name, s.student_id, c.course_name " +
                     "FROM grades g " +
                     "JOIN enrollments e ON g.enrollment_id = e.id " +
                     "JOIN students s ON e.student_id = s.id " +
                     "JOIN courses c ON e.course_id = c.id " +
                     "WHERE s.student_id = ?"; 
        return fetchGradesWithSql(sql, username);
    }

    /**
     * Helper to avoid repeating the Mapping logic for different queries
     */
    private List<Grade> fetchGradesWithSql(String sql, String param) {
        List<Grade> grades = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (param != null) stmt.setString(1, param);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Grade grade = new Grade();
                grade.setId(rs.getInt("id"));
                grade.setScore(rs.getDouble("score"));
                grade.setGradeType(GradeType.valueOf(rs.getString("grade_type")));

                // Hydrate nested objects to fix "N/A"
                Student student = new Student();
                student.setFullName(rs.getString("full_name"));
                student.setStudentId(rs.getString("student_id"));

                Course course = new Course();
                course.setCourseName(rs.getString("course_name"));

                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                
                grade.setEnrollment(enrollment);
                grades.add(grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    @Override
    public void addGrade(Grade grade) {
        String sql = "INSERT INTO grades (enrollment_id, grade_type, score) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, grade.getEnrollmentId());
            stmt.setString(2, grade.getGradeType().name());
            stmt.setDouble(3, grade.getScore());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error adding grade", e);
        }
    }

    @Override public void updateGrade(Grade grade) { /* Implement similarly to addGrade */ }
    @Override public void deleteGrade(int gradeId) { 
        String sql = "DELETE FROM grades WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gradeId);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    @Override public Grade getGradeById(int id) { return null; }
    @Override public List<Grade> getGradesByEnrollmentId(int id) { return new ArrayList<>(); }
}