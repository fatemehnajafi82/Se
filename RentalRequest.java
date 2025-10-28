package com.library;

public class RentalRequest {
    private int requestId;
    private Student student;
    private Book book;
    private String startDate;
    private String endDate;
    private boolean approved;
    private boolean returned;
    private String borrowDate;  // when employee records the borrow
    private String returnDate;  // when student returned the book
    
    public RentalRequest(int requestId, Student student, Book book, String startDate, String endDate) {
        this.requestId = requestId;
        this.student = student;
        this.book = book;
        this.startDate = startDate;
        this.endDate = endDate;
        this.approved = false;
        this.returned = false;
    }
    
    public int getRequestId() {
        return requestId;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public Book getBook() {
        return book;
    }
    
    public String getStartDate() {
        return startDate;
    }
    
    public String getEndDate() {
        return endDate;
    }
    
    public boolean isApproved() {
        return approved;
    }
    
    public void setApproved(boolean approved) {
        this.approved = approved;
    }
    
    public boolean isReturned() {
        return returned;
    }
    
    public void setReturned(boolean returned) {
        this.returned = returned;
    }
    
    public String getBorrowDate() {
        return borrowDate;
    }
    
    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }
    
    public String getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    
    public boolean isDelayed() {
        // Check if book was returned late
        if (returned && returnDate != null && returnDate.compareTo(endDate) > 0) {
            return true;
        }
        return false;
    }
    
    public int getDaysDelayed() {
        if (returned && returnDate != null && isDelayed()) {
            // Simple comparison - in real system would parse dates properly
            return 0; // Simplified for Phase 1
        }
        return 0;
    }
}

