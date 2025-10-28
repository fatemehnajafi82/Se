package com.library;

public class Book {
    private String title;
    private String author;
    private int publicationYear;
    private boolean isBorrowed;
    private int bookId;
    
    public Book(int bookId, String title, String author, int publicationYear) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isBorrowed = false;
    }
    
    public int getBookId() {
        return bookId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public int getPublicationYear() {
        return publicationYear;
    }
    
    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
    
    public boolean isBorrowed() {
        return isBorrowed;
    }
    
    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }
    
    @Override
    public String toString() {
        return "ID: " + bookId + ", Title: " + title + ", Author: " + author + 
               ", Year: " + publicationYear + ", Available: " + (!isBorrowed ? "Yes" : "No");
    }
}

