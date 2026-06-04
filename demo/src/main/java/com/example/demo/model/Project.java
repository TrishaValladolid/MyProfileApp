package com.example.demo.model;

public class Project {
    private int projectId;
    private String projectName;
    private String technologies;
    private String startDate;
    private String endDate;

    // Getters
    public int getProjectId() { return projectId; }
    public String getProjectName() { return projectName; }
    public String getTechnologies() { return technologies; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }

    // Setters
    public void setProjectId(int projectId) { this.projectId = projectId; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public void setTechnologies(String technologies) { this.technologies = technologies; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
}