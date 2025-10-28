package com.library;

import java.util.*;

public class LibrarySystem {
    private List<Student> students;
    private List<Employee> employees;
    private List<Book> books;
    private List<RentalRequest> rentalRequests;
    private Admin admin;
    private int nextStudentId = 1;
    private int nextEmployeeId = 1;
    private int nextBookId = 1;
    private int nextRequestId = 1;
    
    public LibrarySystem() {
        this.students = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.books = new ArrayList<>();
        this.rentalRequests = new ArrayList<>();
        
        // Initialize with default admin
        this.admin = new Admin("admin", "admin");
        
        // Add some sample data
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        // Sample books
        books.add(new Book(nextBookId++, "Programming in Java", "John Doe", 2020));
        books.add(new Book(nextBookId++, "Data Structures", "Jane Smith", 2019));
        books.add(new Book(nextBookId++, "Algorithms", "Bob Wilson", 2021));
        books.add(new Book(nextBookId++, "Database Design", "Alice Brown", 2018));
        
        // Sample student
        students.add(new Student("student1", "pass123", "Ahmad Rezaei", nextStudentId++));
        
        // Sample employee
        employees.add(new Employee("emp1", "emp123", "Fatemeh Karimi", nextEmployeeId++));
    }
    
    // Student methods
    public boolean registerStudent(String username, String password, String name) {
        // Check if username already exists
        for (Student s : students) {
            if (s.getUsername().equals(username)) {
                return false;
            }
        }
        students.add(new Student(username, password, name, nextStudentId++));
        return true;
    }
    
    public Student loginStudent(String username, String password) {
        for (Student student : students) {
            if (student.login(username, password)) {
                return student;
            }
        }
        return null;
    }
    
    public List<Book> searchBooks(String title, String author, Integer year) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            boolean match = true;
            if (title != null && !title.isEmpty()) {
                match = match && book.getTitle().toLowerCase().contains(title.toLowerCase());
            }
            if (author != null && !author.isEmpty()) {
                match = match && book.getAuthor().toLowerCase().contains(author.toLowerCase());
            }
            if (year != null) {
                match = match && book.getPublicationYear() == year;
            }
            if (match) {
                results.add(book);
            }
        }
        return results;
    }
    
    public boolean addRentalRequest(Student student, int bookId, String startDate, String endDate) {
        if (!student.isActive()) {
            return false;
        }
        
        Book book = findBookById(bookId);
        if (book == null || book.isBorrowed()) {
            return false;
        }
        
        rentalRequests.add(new RentalRequest(nextRequestId++, student, book, startDate, endDate));
        return true;
    }
    
    // Guest methods
    public int getTotalStudents() {
        return students.size();
    }
    
    public List<Book> searchBooksByTitle(String title) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }
    
    public int getTotalBooks() {
        return books.size();
    }
    
    public int getTotalRentals() {
        return rentalRequests.size();
    }
    
    public int getCurrentlyBorrowedBooks() {
        int count = 0;
        for (RentalRequest request : rentalRequests) {
            if (request.isApproved() && !request.isReturned()) {
                count++;
            }
        }
        return count;
    }
    
    // Employee methods
    public Employee loginEmployee(String username, String password) {
        for (Employee emp : employees) {
            if (emp.login(username, password)) {
                return emp;
            }
        }
        return null;
    }
    
    public boolean changeEmployeePassword(Employee employee, String oldPassword, String newPassword) {
        if (employee.getPassword().equals(oldPassword)) {
            employee.setPassword(newPassword);
            return true;
        }
        return false;
    }
    
    public boolean addBook(Employee employee, String title, String author, int year) {
        books.add(new Book(nextBookId++, title, author, year));
        employee.incrementBooksRegistered();
        return true;
    }
    
    public Book findBookById(int bookId) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                return book;
            }
        }
        return null;
    }
    
    public boolean updateBook(int bookId, String title, String author, Integer year) {
        Book book = findBookById(bookId);
        if (book == null) return false;
        
        if (title != null && !title.isEmpty()) {
            book.setTitle(title);
        }
        if (author != null && !author.isEmpty()) {
            book.setAuthor(author);
        }
        if (year != null && year > 0) {
            book.setPublicationYear(year);
        }
        return true;
    }
    
    public List<RentalRequest> getPendingRentalRequests(String currentDate) {
        List<RentalRequest> pending = new ArrayList<>();
        for (RentalRequest request : rentalRequests) {
            if (!request.isApproved() && 
                (request.getStartDate().equals(currentDate) || 
                 request.getStartDate().compareTo(currentDate) < 0)) {
                pending.add(request);
            }
        }
        return pending;
    }
    
    public boolean approveRentalRequest(int requestId, Employee employee, String borrowDate) {
        RentalRequest request = findRentalRequestById(requestId);
        if (request != null && !request.isApproved()) {
            request.setApproved(true);
            request.getBook().setBorrowed(true);
            request.setBorrowDate(borrowDate);
            employee.incrementBooksLent();
            return true;
        }
        return false;
    }
    
    public List<RentalRequest> getStudentRentalHistory(Student student) {
        List<RentalRequest> history = new ArrayList<>();
        for (RentalRequest request : rentalRequests) {
            if (request.getStudent().equals(student)) {
                history.add(request);
            }
        }
        return history;
    }
    
    public boolean setStudentActiveStatus(Student student, boolean active) {
        student.setActive(active);
        return true;
    }
    
    public Student findStudentByUsername(String username) {
        for (Student student : students) {
            if (student.getUsername().equals(username)) {
                return student;
            }
        }
        return null;
    }
    
    public boolean recordBookReturn(int requestId, Employee employee, String returnDate) {
        RentalRequest request = findRentalRequestById(requestId);
        if (request != null && request.isApproved() && !request.isReturned()) {
            request.setReturned(true);
            request.getBook().setBorrowed(false);
            request.setReturnDate(returnDate);
            employee.incrementBooksReturned();
            return true;
        }
        return false;
    }
    
    // Admin methods
    public boolean loginAdmin(String username, String password) {
        return admin.login(username, password);
    }
    
    public boolean addEmployee(String username, String password, String name) {
        // Check if username already exists
        for (Employee e : employees) {
            if (e.getUsername().equals(username)) {
                return false;
            }
        }
        employees.add(new Employee(username, password, name, nextEmployeeId++));
        return true;
    }
    
    public List<Employee> getAllEmployees() {
        return employees;
    }
    
    public List<Student> getAllStudents() {
        return students;
    }
    
    public int getTotalRentalRequests() {
        return rentalRequests.size();
    }
    
    public int getTotalApprovedRentals() {
        int count = 0;
        for (RentalRequest request : rentalRequests) {
            if (request.isApproved()) {
                count++;
            }
        }
        return count;
    }
    
    public double getAverageRentalDays() {
        int totalDays = 0;
        int count = 0;
        for (RentalRequest request : rentalRequests) {
            if (request.isReturned() && request.getBorrowDate() != null && request.getReturnDate() != null) {
                // Simplified calculation for Phase 1
                totalDays += 7; // Assume 7 days average
                count++;
            }
        }
        return count > 0 ? (double) totalDays / count : 0;
    }
    
    private RentalRequest findRentalRequestById(int requestId) {
        for (RentalRequest request : rentalRequests) {
            if (request.getRequestId() == requestId) {
                return request;
            }
        }
        return null;
    }
}

