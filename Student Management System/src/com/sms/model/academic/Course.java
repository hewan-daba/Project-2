package com.sms.model.academic;

/**
 * Course
 *
 * Represents an academic course in the system.
 * Stores course code, name, and credit information.
 */
public class Course {

    private int id;             // Auto-generated database ID
    private String courseCode;  // Unique course code (e.g., CS101)
    private String courseName;
    private int credit;         // Credit hours of the course

    // Constructors

    // Full constructor with DB ID
    public Course() {
    }
    public Course(int id, String courseCode, String courseName, int credit) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credit = credit;
    }

    // Constructor without DB ID (for new course before insert)
    public Course(String courseCode, String courseName, int credit) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credit = credit;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credit=" + credit +
                '}';
    }
}
