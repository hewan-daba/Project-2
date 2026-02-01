package com.sms.service.interfaces;

import com.sms.model.academic.Grade;
import com.sms.model.academic.Enrollment;
import com.sms.exception.DataAccessException;
import com.sms.service.interfaces.GradeService;

import java.util.List;

/**
 * GradeService

 * Provides methods to manage grades in the system.
 */
public interface GradeService {

    /**
     * Add a new grade for an enrollment.
     *
     * @param grade The grade to add
     * @throws DataAccessException if the operation fails
     */
    void addGrade(Grade grade) throws DataAccessException;

    /**
     * Update an existing grade.
     *
     * @param grade The grade to update
     * @throws DataAccessException if the operation fails
     */
    void updateGrade(Grade grade) throws DataAccessException;

    /**
     * Delete a grade by its ID.
     *
     * @param gradeId The ID of the grade
     * @throws DataAccessException if the operation fails
     */
    void deleteGrade(int gradeId) throws DataAccessException;

    /**
     * Get a list of grades for a specific enrollment.
     *
     * @param enrollment The enrollment to get grades for
     * @return List of grades
     * @throws DataAccessException if the operation fails
     */
    List<Grade> getGradesByEnrollment(Enrollment enrollment) throws DataAccessException;
    List<Grade> getGradesByStudentUsername(String username);

    /**
     * Get a grade by its ID.
     *
     * @param gradeId The ID of the grade
     * @return Grade object
     * @throws DataAccessException if the operation fails
     */
    Grade getGradeById(int gradeId) throws DataAccessException;

    double calculateFinalGrade(int enrollmentId);

    /**
     * Get all grades in the system.
     *
     * @return List of all grades
     * @throws DataAccessException if the operation fails
     */
    List<Grade> getAllGrades() throws DataAccessException;
}


