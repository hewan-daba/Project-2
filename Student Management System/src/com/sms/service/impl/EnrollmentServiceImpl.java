package com.sms.service.impl;

import com.sms.dao.interfaces.EnrollmentDAO;
import com.sms.dao.interfaces.StudentDAO;
import com.sms.dao.interfaces.CourseDAO;
import com.sms.model.academic.Enrollment;
import com.sms.model.enums.EnrollmentStatus;
import com.sms.service.interfaces.EnrollmentService;
import com.sms.exception.ValidationException;
import com.sms.service.interfaces.StudentService;
import com.sms.dao.impl.EnrollmentDAOImpl;
import com.sms.dao.impl.StudentDAOImpl;
import com.sms.dao.impl.CourseDAOImpl;


import java.util.List;

/**
 * EnrollmentServiceImpl
 *
 * Implements EnrollmentService for managing student enrollments.
 */
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentDAO enrollmentDAO;
    private final StudentDAO studentDAO;
    private final CourseDAO courseDAO;

    /**
     * Constructor injection for DAO dependencies.
     *
     * @param enrollmentDAO EnrollmentDAO implementation
     * @param studentDAO    StudentDAO implementation
     * @param courseDAO     CourseDAO implementation
     */
    public EnrollmentServiceImpl(EnrollmentDAO enrollmentDAO, StudentDAO studentDAO, CourseDAO courseDAO) {
        this.enrollmentDAO = enrollmentDAO;
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
    }
    public EnrollmentServiceImpl() {
        this.enrollmentDAO = new EnrollmentDAOImpl();
        this.studentDAO = new StudentDAOImpl();
        this.courseDAO = new CourseDAOImpl();
    }


    /**
     * Add a new enrollment
     *
     * @param enrollment Enrollment object
     */
    @Override
    public void addEnrollment(Enrollment enrollment) throws ValidationException {

        // Validation: check student exists
        if (studentDAO.getStudentById(enrollment.getStudentId()) == null) {
            throw new ValidationException("Student does not exist.");
        }

        // Validation: check course exists
        if (courseDAO.getCourseById(enrollment.getCourseId()) == null) {
            throw new ValidationException("Course does not exist.");
        }

        // Default status if null
        if (enrollment.getStatus() == null) {
            enrollment.setStatus(EnrollmentStatus.PENDING);
        }

        enrollmentDAO.addEnrollment(enrollment);
    }

    /**
     * Update an existing enrollment
     *
     * @param enrollment Enrollment object
     */
    @Override
    public void enrollStudent(Enrollment enrollment) throws ValidationException {
        addEnrollment(enrollment); // delegate to addEnrollment or implement separate logic
    }

    @Override
    public void updateEnrollment(Enrollment enrollment) throws ValidationException {
        if (enrollmentDAO.getEnrollmentById(enrollment.getId()) == null) {
            throw new ValidationException("Enrollment does not exist.");
        }
        enrollmentDAO.updateEnrollment(enrollment);
    }

    /**
     * Delete an enrollment by ID
     *
     * @param enrollmentId Enrollment ID
     */
    @Override
    public void deleteEnrollment(int enrollmentId) throws ValidationException {
        if (enrollmentDAO.getEnrollmentById(enrollmentId) == null) {
            throw new RuntimeException("Enrollment does not exist.");
        }
        enrollmentDAO.deleteEnrollment(enrollmentId);
    }

    /**
     * Get an enrollment by ID
     *
     * @param enrollmentId Enrollment ID
     * @return Enrollment object
     */
    @Override
    public Enrollment getEnrollmentById(int enrollmentId) {
        return enrollmentDAO.getEnrollmentById(enrollmentId);
    }

    /**
     * Get all enrollments
     *
     * @return List of Enrollment
     */
    @Override
    public List<Enrollment> getAllEnrollments() {
        return enrollmentDAO.getAllEnrollments();
    }

    /**
     * Get enrollments by student ID
     *
     * @param studentId Student ID
     * @return List of Enrollment
     */
    @Override
    public List<Enrollment> getEnrollmentsByStudent(int studentId) {
        return enrollmentDAO.getEnrollmentsByStudentId(studentId);
    }

    /**
     * Get enrollments by course ID
     *
     * @param courseId Course ID
     * @return List of Enrollment
     */
    @Override
    public List<Enrollment> getEnrollmentsByCourse(int courseId) {
        return enrollmentDAO.getEnrollmentsByCourseId(courseId);
    }
}


