package com.example.demo.model;

public class Skill {
    private int skillsId;
    private String skill;
    private String level;
    private int levelValue;

    // Getters
    public int getSkillsId() { return skillsId; }
    public String getSkill() { return skill; }
    public String getLevel() { return level; }
    public int getLevelValue() { return levelValue; }

    // Setters
    public void setSkillsId(int skillsId) { this.skillsId = skillsId; }
    public void setSkill(String skill) { this.skill = skill; }
    public void setLevel(String level) {
        this.level = level;
        switch (level) {
            case "Expert":       this.levelValue = 4; break;
            case "Advanced":     this.levelValue = 3; break;
            case "Intermediate": this.levelValue = 2; break;
            default:             this.levelValue = 1; break;
        }
    }
}