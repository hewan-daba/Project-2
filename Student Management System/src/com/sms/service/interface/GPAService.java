package com.sms.service.interfaces;

import com.sms.model.academic.Student;
import com.sms.exception.DataAccessException;

/**
 * GPAService
 *
 * Provides methods to calculate GPA for students.
 */
public interface GPAService {

    /**
     * Calculate the GPA of a student based on their grades.
     *
     * @param student The student whose GPA is calculated
     * @return GPA as a double
     * @throws DataAccessException if grades cannot be retrieved
     */
    double calculateGPA(Student student) throws DataAccessException;
}

