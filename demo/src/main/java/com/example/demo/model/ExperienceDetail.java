package com.example.demo.model;

public class ExperienceDetail {
    private int experienceDetailId;
    private String description;
    private int experienceId;

    // Getters
    public int getExperienceDetailId() { return experienceDetailId; }
    public String getDescription() { return description; }
    public int getExperienceId() { return experienceId; }

    // Setters
    public void setExperienceDetailId(int experienceDetailId) { this.experienceDetailId = experienceDetailId; }
    public void setDescription(String description) { this.description = description; }
    public void setExperienceId(int experienceId) { this.experienceId = experienceId; }
}