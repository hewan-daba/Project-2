package com.sms.service.impl;

import com.sms.model.academic.Student;
import com.sms.model.academic.Course;
import com.sms.model.academic.Enrollment;
import com.sms.exception.DataAccessException;
import com.sms.model.academic.Grade;
import com.sms.service.interfaces.ExportService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * CSVExportServiceImpl
 *
 * Implements ExportService for exporting academic data to CSV files.
 */
public class CSVExportServiceImpl<T> implements ExportService<T> {

    /**
     * Export a list of Students to CSV.
     *
     * @param students List of students
     * @param filePath Output CSV file path
     */

    public void exportStudents(List<Student> students, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            // Header
            writer.append("ID,Student ID,Full Name,Email,Phone\n");

            // Data
            for (Student s : students) {
                writer.append(s.getId() + ",")
                        .append(s.getStudentId() + ",")
                        .append(s.getFullName() + ",")
                        .append(s.getEmail() + ",")
                        .append(s.getPhone() + "\n");
            }

            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Export a list of Courses to CSV.
     *
     * @param courses  List of courses
     * @param filePath Output CSV file path
     */
    public void exportCourses(List<Course> courses, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            writer.append("ID,Course Code,Course Name,Credit\n");

            for (Course c : courses) {
                writer.append(c.getId() + ",")
                        .append(c.getCourseCode() + ",")
                        .append(c.getCourseName() + ",")
                        .append(c.getCredit() + "\n");
            }

            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Export a list of Enrollments to CSV.
     *
     * @param enrollments List of enrollments
     * @param filePath    Output CSV file path
     */
    public void exportEnrollments(List<Enrollment> enrollments, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            writer.append("ID,Student ID,Course ID,Status\n");

            for (Enrollment e : enrollments) {
                writer.append(e.getId() + ",")
                        .append(e.getStudentId() + ",")
                        .append(e.getCourseId() + ",")
                        .append(e.getStatus().name() + "\n");
            }

            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Export a list of Grades to CSV.
     *
     * @param filePath Output CSV file path
     */
    @Override
    public void exportToCSV(List<T> data, String filePath) throws DataAccessException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (T item : data) {
                writer.write(item.toString()); // customize formatting if needed
                writer.newLine();
            }
        } catch (IOException e) {
            throw new DataAccessException("Error exporting to CSV: " + e.getMessage(), e);
        }
    }


    public void exportGrades(List<Grade> grades, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            writer.append("ID,Enrollment ID,Grade Type,Score\n");

            for (Grade g : grades) {
                writer.append(g.getId() + ",")
                        .append(g.getEnrollmentId() + ",")
                        .append(g.getGradeType().name() + ",")
                        .append(g.getScore() + "\n");
            }

            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


