package com.example.demo.model;

public class ProjectDetail {
    private int projectDetailId;
    private String description;
    private int projectId;

    // Getters
    public int getProjectDetailId() { return projectDetailId; }
    public String getDescription() { return description; }
    public int getProjectId() { return projectId; }

    // Setters
    public void setProjectDetailId(int projectDetailId) { this.projectDetailId = projectDetailId; }
    public void setDescription(String description) { this.description = description; }
    public void setProjectId(int projectId) { this.projectId = projectId; }
}