package com.library;

public class Student extends User {
    private String name;
    private boolean active;
    private int studentId;
    
    public Student(String username, String password, String name, int studentId) {
        super(username, password);
        this.name = name;
        this.studentId = studentId;
        this.active = true; // Active by default
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public int getStudentId() {
        return studentId;
    }
}

