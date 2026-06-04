package com.example.demo.model;

public class Person {
    private String firstName;
    private String lastName;
    private String location;
    private String email;
    private String phone;
    private String shortIntroduction;
    private String longIntroduction;

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getLocation() { return location; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getShortIntroduction() { return shortIntroduction; }
    public String getLongIntroduction() { return longIntroduction; }

    // Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setLocation(String location) { this.location = location; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setShortIntroduction(String shortIntroduction) { this.shortIntroduction = shortIntroduction; }
    public void setLongIntroduction(String longIntroduction) { this.longIntroduction = longIntroduction; }
}