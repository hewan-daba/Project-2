package com.sms.service.interfaces;

import com.sms.exception.ValidationException;
import com.sms.model.academic.Enrollment;

import java.util.List;

/**
 * EnrollmentService
 *
 * Provides operations to manage student enrollments in courses.
 */
public interface EnrollmentService {

    /**
     * Enroll a student in a course.
     *
     * @param enrollment Enrollment object containing student & course info
     * @throws ValidationException if enrollment is invalid (e.g., student already enrolled)
     */
    void addEnrollment(Enrollment enrollment) throws ValidationException;

    void enrollStudent(Enrollment enrollment) throws ValidationException;

    /**
     * Update an existing enrollment (e.g., status change).
     *
     * @param enrollment Enrollment object with updated information
     * @throws ValidationException if update fails
     */
    void updateEnrollment(Enrollment enrollment) throws ValidationException;

    /**
     * Delete an enrollment by its ID.
     *
     * @param enrollmentId ID of the enrollment to delete
     */
    void deleteEnrollment(int enrollmentId);

    /**
     * Get an enrollment by ID.
     *
     * @param enrollmentId ID of the enrollment
     * @return Enrollment object
     */
    Enrollment getEnrollmentById(int enrollmentId);

    /**
     * Get all enrollments.
     *
     * @return List of all enrollments
     */
    List<Enrollment> getAllEnrollments();
    List<Enrollment> getEnrollmentsByStudent(int studentId);
    List<Enrollment> getEnrollmentsByCourse(int courseId);

}


