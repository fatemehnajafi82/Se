package com.library;

public class Employee extends User {
    private String name;
    private int employeeId;
    private int booksRegistered;
    private int booksLent;
    private int booksReturned;
    
    public Employee(String username, String password, String name, int employeeId) {
        super(username, password);
        this.name = name;
        this.employeeId = employeeId;
        this.booksRegistered = 0;
        this.booksLent = 0;
        this.booksReturned = 0;
    }
    
    public String getName() {
        return name;
    }
    
    public int getEmployeeId() {
        return employeeId;
    }
    
    public int getBooksRegistered() {
        return booksRegistered;
    }
    
    public void incrementBooksRegistered() {
        this.booksRegistered++;
    }
    
    public int getBooksLent() {
        return booksLent;
    }
    
    public void incrementBooksLent() {
        this.booksLent++;
    }
    
    public int getBooksReturned() {
        return booksReturned;
    }
    
    public void incrementBooksReturned() {
        this.booksReturned++;
    }
}

