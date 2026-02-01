package com.sms.model.academic;

import com.sms.model.enums.GradeType;

/**
 * Grade
 *
 * Represents the grade for a specific enrollment.
 * Stores enrollment ID, grade type, and numeric score.
 */
public class Grade {

    private Enrollment enrollment;
    private int id;             // Auto-generated database ID
    private int enrollmentId;   // Reference to Enrollment.id
    private GradeType gradeType;
    private double score;

    // Constructors

    // Full constructor with DB ID
    public Grade(int id, int enrollmentId, GradeType gradeType, double score) {
        this.id = id;
        this.enrollmentId = enrollmentId;
        this.gradeType = gradeType;
        this.score = score;
    }

    // Constructor without DB ID (for new grade before insert)
    public Grade(int enrollmentId, GradeType gradeType, double score) {
        this.enrollmentId = enrollmentId;
        this.gradeType = gradeType;
        this.score = score;
    }
    public Grade() {
        // Optional: initialize default values
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public GradeType getGradeType() {
        return gradeType;
    }

    public void setGradeType(GradeType gradeType) {
        this.gradeType = gradeType;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", enrollmentId=" + enrollmentId +
                ", gradeType=" + gradeType +
                ", score=" + score +
                '}';
    }
}

