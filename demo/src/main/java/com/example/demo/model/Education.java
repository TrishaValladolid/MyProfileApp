package com.example.demo.model;

public class Education {
    private int educationId;
    private String schoolName;
    private String location;
    private String degree;
    private String course;
    private String years;

    // Getters
    public int getEducationId() { return educationId; }
    public String getSchoolName() { return schoolName; }
    public String getLocation() { return location; }
    public String getDegree() { return degree; }
    public String getCourse() { return course; }
    public String getYears() { return years; }

    // Setters
    public void setEducationId(int educationId) { this.educationId = educationId; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
    public void setLocation(String location) { this.location = location; }
    public void setDegree(String degree) { this.degree = degree; }
    public void setCourse(String course) { this.course = course; }
    public void setYears(String years) { this.years = years; }
}