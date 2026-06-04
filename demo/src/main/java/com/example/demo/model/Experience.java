package com.example.demo.model;

public class Experience {
    private int experienceId;
    private String organization;
    private String location;
    private String years;
    private String role;

    // Getters
    public int getExperienceId() { return experienceId; }
    public String getOrganization() { return organization; }
    public String getLocation() { return location; }
    public String getYears() { return years; }
    public String getRole() { return role; }

    // Setters
    public void setExperienceId(int experienceId) { this.experienceId = experienceId; }
    public void setOrganization(String organization) { this.organization = organization; }
    public void setLocation(String location) { this.location = location; }
    public void setYears(String years) { this.years = years; }
    public void setRole(String role) { this.role = role; }
}