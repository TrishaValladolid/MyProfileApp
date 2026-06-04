package com.example.demo.model;

public class EducationDetail {
    private int educationDetailId;
    private int educationId;
    private String coursework;
    private String description;

    // Getters
    public int getEducationDetailId() { return educationDetailId; }
    public int getEducationId() { return educationId; }
    public String getCoursework() { return coursework; }
    public String getDescription() { return description; }

    // Setters
    public void setEducationDetailId(int educationDetailId) { this.educationDetailId = educationDetailId; }
    public void setEducationId(int educationId) { this.educationId = educationId; }
    public void setCoursework(String coursework) { this.coursework = coursework; }
    public void setDescription(String description) { this.description = description; }
}