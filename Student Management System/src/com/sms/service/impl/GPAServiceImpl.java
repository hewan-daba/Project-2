package com.sms.service.impl;

import com.sms.dao.interfaces.EnrollmentDAO;
import com.sms.model.academic.Student;
import com.sms.dao.interfaces.GradeDAO;
import com.sms.model.academic.Enrollment;
import com.sms.model.academic.Grade;
import com.sms.service.interfaces.GPAService;
import com.sms.exception.DataAccessException;

import java.util.List;

/**
 * GPAServiceImpl
 *
 * Implements GPAService to calculate GPA for students.
 */
public class GPAServiceImpl implements GPAService {

    private final EnrollmentDAO enrollmentDAO;
    private final GradeDAO gradeDAO;

    /**
     * Constructor injection for DAO dependencies.
     *
     * @param enrollmentDAO EnrollmentDAO implementation
     * @param gradeDAO      GradeDAO implementation
     */
    public GPAServiceImpl(EnrollmentDAO enrollmentDAO, GradeDAO gradeDAO) {
        this.enrollmentDAO = enrollmentDAO;
        this.gradeDAO = gradeDAO;
    }

    /**
     * Calculate GPA for a student by ID.
     *
     * Formula: sum(grade * credit) / sum(credits)
     *
     * @return GPA as double (0.0 if no grades)
     */
    @Override
    public double calculateGPA(Student student) throws DataAccessException {
        if (student == null) {
            throw new DataAccessException("Student cannot be null");
        }

        int studentId = student.getId(); // use the student object to get ID

        List<Enrollment> enrollments = enrollmentDAO.getEnrollmentsByStudentId(studentId);
        double totalPoints = 0.0;
        int totalCredits = 0;

        for (Enrollment enrollment : enrollments) {
            List<Grade> grades = gradeDAO.getGradesByEnrollmentId(enrollment.getId());
            int credit = enrollment.getCourse().getCredit();

            for (Grade grade : grades) {
                totalPoints += grade.getScore() * credit;
                totalCredits += credit;
            }
        }

        if (totalCredits == 0) return 0.0;

        return totalPoints / totalCredits;
    }

}


