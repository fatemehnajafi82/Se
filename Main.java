package com.library;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static LibrarySystem librarySystem = new LibrarySystem();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== Library Management System ===\n");
        
        while (true) {
            showMainMenu();
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    guestMenu();
                    break;
                case 2:
                    studentMenu();
                    break;
                case 3:
                    employeeMenu();
                    break;
                case 4:
                    adminMenu();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void showMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Guest Access");
        System.out.println("2. Student Login");
        System.out.println("3. Employee Login");
        System.out.println("4. Admin Login");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }
    
    // GUEST MENU
    private static void guestMenu() {
        while (true) {
            System.out.println("\n=== Guest Menu ===");
            System.out.println("1. View number of registered students");
            System.out.println("2. Search books by title");
            System.out.println("3. View statistics");
            System.out.println("0. Back to main menu");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    showTotalStudents();
                    break;
                case 2:
                    searchBooksGuest();
                    break;
                case 3:
                    showStatisticsGuest();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void showTotalStudents() {
        System.out.println("\nTotal registered students: " + librarySystem.getTotalStudents());
    }
    
    private static void searchBooksGuest() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine().trim();
        
        List<Book> results = librarySystem.searchBooksByTitle(title);
        
        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("\nSearch Results:");
            for (Book book : results) {
                System.out.println("  " + book);
            }
        }
    }
    
    private static void showStatisticsGuest() {
        System.out.println("\n=== Statistics ===");
        System.out.println("Total Students: " + librarySystem.getTotalStudents());
        System.out.println("Total Books: " + librarySystem.getTotalBooks());
        System.out.println("Total Rentals: " + librarySystem.getTotalRentals());
        System.out.println("Currently Borrowed Books: " + librarySystem.getCurrentlyBorrowedBooks());
    }
    
    // STUDENT MENU
    private static void studentMenu() {
        System.out.println("\n=== Student Menu ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("0. Back to main menu");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        
        if (choice == 1) {
            loginStudent();
        } else if (choice == 2) {
            registerStudent();
        }
    }
    
    private static void loginStudent() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        
        Student student = librarySystem.loginStudent(username, password);
        
        if (student != null) {
            if (student.isActive()) {
                studentLoggedInMenu(student);
            } else {
                System.out.println("Your account is inactive. Please contact the library.");
            }
        } else {
            System.out.println("Invalid username or password.");
        }
    }
    
    private static void registerStudent() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();
        
        if (librarySystem.registerStudent(username, password, name)) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Username already exists.");
        }
    }
    
    private static void studentLoggedInMenu(Student student) {
        while (true) {
            System.out.println("\n=== Student Menu - Welcome " + student.getName() + " ===");
            System.out.println("1. Search books");
            System.out.println("2. Request book rental");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    searchBooksStudent();
                    break;
                case 2:
                    requestBookRental(student);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void searchBooksStudent() {
        System.out.print("Enter title (or press Enter to skip): ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter author (or press Enter to skip): ");
        String author = scanner.nextLine().trim();
        System.out.print("Enter publication year (or press Enter to skip): ");
        String yearStr = scanner.nextLine().trim();
        
        Integer year = null;
        if (!yearStr.isEmpty()) {
            try {
                year = Integer.parseInt(yearStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid year format.");
                return;
            }
        }
        
        List<Book> results = librarySystem.searchBooks(title, author, year);
        
        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("\nSearch Results:");
            for (Book book : results) {
                System.out.println("  " + book);
            }
        }
    }
    
    private static void requestBookRental(Student student) {
        System.out.print("Enter book ID: ");
        int bookId = getIntInput();
        
        System.out.print("Enter start date (format: YYYY-MM-DD): ");
        String startDate = scanner.nextLine().trim();
        
        System.out.print("Enter end date (format: YYYY-MM-DD): ");
        String endDate = scanner.nextLine().trim();
        
        if (librarySystem.addRentalRequest(student, bookId, startDate, endDate)) {
            System.out.println("Rental request submitted successfully!");
        } else {
            System.out.println("Failed to submit rental request. Book may not be available or you are inactive.");
        }
    }
    
    // EMPLOYEE MENU
    private static void employeeMenu() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        
        Employee employee = librarySystem.loginEmployee(username, password);
        
        if (employee != null) {
            employeeLoggedInMenu(employee);
        } else {
            System.out.println("Invalid username or password.");
        }
    }
    
    private static void employeeLoggedInMenu(Employee employee) {
        while (true) {
            System.out.println("\n=== Employee Menu - Welcome " + employee.getName() + " ===");
            System.out.println("1. Change password");
            System.out.println("2. Add book");
            System.out.println("3. Search and edit book");
            System.out.println("4. Approve rental requests");
            System.out.println("5. View student rental history");
            System.out.println("6. Activate/Deactivate student");
            System.out.println("7. Record book return");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    changeEmployeePassword(employee);
                    break;
                case 2:
                    addBook(employee);
                    break;
                case 3:
                    searchAndEditBook();
                    break;
                case 4:
                    approveRentalRequests(employee);
                    break;
                case 5:
                    viewStudentRentalHistory();
                    break;
                case 6:
                    activateDeactivateStudent();
                    break;
                case 7:
                    recordBookReturn(employee);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void changeEmployeePassword(Employee employee) {
        System.out.print("Enter old password: ");
        String oldPassword = scanner.nextLine().trim();
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine().trim();
        
        if (librarySystem.changeEmployeePassword(employee, oldPassword, newPassword)) {
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Old password is incorrect.");
        }
    }
    
    private static void addBook(Employee employee) {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter author: ");
        String author = scanner.nextLine().trim();
        System.out.print("Enter publication year: ");
        int year = getIntInput();
        
        if (librarySystem.addBook(employee, title, author, year)) {
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Failed to add book.");
        }
    }
    
    private static void searchAndEditBook() {
        System.out.print("Enter book ID: ");
        int bookId = getIntInput();
        
        Book book = librarySystem.findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        
        System.out.println("Current book information:");
        System.out.println(book);
        
        System.out.print("Enter new title (or press Enter to skip): ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter new author (or press Enter to skip): ");
        String author = scanner.nextLine().trim();
        System.out.print("Enter new publication year (or 0 to skip): ");
        int year = getIntInput();
        
        if (librarySystem.updateBook(bookId, title, author, year == 0 ? null : year)) {
            System.out.println("Book information updated successfully!");
        } else {
            System.out.println("Failed to update book.");
        }
    }
    
    private static void approveRentalRequests(Employee employee) {
        System.out.print("Enter current date (format: YYYY-MM-DD): ");
        String currentDate = scanner.nextLine().trim();
        
        List<RentalRequest> pending = librarySystem.getPendingRentalRequests(currentDate);
        
        if (pending.isEmpty()) {
            System.out.println("No pending rental requests for today or earlier.");
            return;
        }
        
        System.out.println("\nPending Rental Requests:");
        for (RentalRequest request : pending) {
            System.out.println("ID: " + request.getRequestId() + 
                             ", Student: " + request.getStudent().getName() +
                             ", Book: " + request.getBook().getTitle() +
                             ", Start: " + request.getStartDate() +
                             ", End: " + request.getEndDate());
        }
        
        System.out.print("\nEnter request ID to approve (or 0 to cancel): ");
        int requestId = getIntInput();
        
        if (requestId > 0) {
            System.out.print("Enter borrow date (format: YYYY-MM-DD): ");
            String borrowDate = scanner.nextLine().trim();
            
            if (librarySystem.approveRentalRequest(requestId, employee, borrowDate)) {
                System.out.println("Rental request approved successfully!");
            } else {
                System.out.println("Failed to approve rental request.");
            }
        }
    }
    
    private static void viewStudentRentalHistory() {
        System.out.print("Enter student username: ");
        String username = scanner.nextLine().trim();
        
        Student student = librarySystem.findStudentByUsername(username);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        List<RentalRequest> history = librarySystem.getStudentRentalHistory(student);
        
        System.out.println("\n=== Rental History for " + student.getName() + " ===");
        
        int totalRentals = history.size();
        int notReturned = 0;
        int delayed = 0;
        
        for (RentalRequest request : history) {
            if (!request.isReturned()) notReturned++;
            if (request.isDelayed()) delayed++;
            
            System.out.println("Request ID: " + request.getRequestId() +
                             ", Book: " + request.getBook().getTitle() +
                             ", Start: " + request.getStartDate() +
                             ", End: " + request.getEndDate() +
                             ", Approved: " + (request.isApproved() ? "Yes" : "No") +
                             ", Returned: " + (request.isReturned() ? "Yes" : "No") +
                             (request.isDelayed() ? " [DELAYED]" : ""));
        }
        
        System.out.println("\nStatistics:");
        System.out.println("Total rentals: " + totalRentals);
        System.out.println("Not returned: " + notReturned);
        System.out.println("Delayed returns: " + delayed);
    }
    
    private static void activateDeactivateStudent() {
        System.out.print("Enter student username: ");
        String username = scanner.nextLine().trim();
        
        Student student = librarySystem.findStudentByUsername(username);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        System.out.println("Current status: " + (student.isActive() ? "Active" : "Inactive"));
        System.out.print("Set new status (1=Active, 0=Inactive): ");
        int status = getIntInput();
        
        librarySystem.setStudentActiveStatus(student, status == 1);
        System.out.println("Student status updated successfully!");
    }
    
    private static void recordBookReturn(Employee employee) {
        System.out.print("Enter rental request ID: ");
        int requestId = getIntInput();
        
        System.out.print("Enter return date (format: YYYY-MM-DD): ");
        String returnDate = scanner.nextLine().trim();
        
        if (librarySystem.recordBookReturn(requestId, employee, returnDate)) {
            System.out.println("Book return recorded successfully!");
        } else {
            System.out.println("Failed to record book return.");
        }
    }
    
    // ADMIN MENU
    private static void adminMenu() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        
        if (librarySystem.loginAdmin(username, password)) {
            adminLoggedInMenu();
        } else {
            System.out.println("Invalid username or password.");
        }
    }
    
    private static void adminLoggedInMenu() {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Add employee");
            System.out.println("2. View employee performance");
            System.out.println("3. View rental statistics");
            System.out.println("4. View all student statistics");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewEmployeePerformance();
                    break;
                case 3:
                    viewRentalStatistics();
                    break;
                case 4:
                    viewAllStudentStatistics();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void addEmployee() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        
        if (librarySystem.addEmployee(username, password, name)) {
            System.out.println("Employee added successfully!");
        } else {
            System.out.println("Failed to add employee. Username may already exist.");
        }
    }
    
    private static void viewEmployeePerformance() {
        List<Employee> employees = librarySystem.getAllEmployees();
        
        System.out.println("\n=== Employee Performance ===");
        for (Employee emp : employees) {
            System.out.println("\nEmployee: " + emp.getName() + " (ID: " + emp.getEmployeeId() + ")");
            System.out.println("Books registered: " + emp.getBooksRegistered());
            System.out.println("Books lent: " + emp.getBooksLent());
            System.out.println("Books returned: " + emp.getBooksReturned());
        }
    }
    
    private static void viewRentalStatistics() {
        System.out.println("\n=== Rental Statistics ===");
        System.out.println("Total rental requests: " + librarySystem.getTotalRentalRequests());
        System.out.println("Total approved rentals: " + librarySystem.getTotalApprovedRentals());
        System.out.println("Average rental days: " + String.format("%.2f", librarySystem.getAverageRentalDays()));
    }
    
    private static void viewAllStudentStatistics() {
        System.out.println("\n=== All Student Statistics ===");
        
        List<Student> students = librarySystem.getAllStudents();
        
        for (Student student : students) {
            List<RentalRequest> history = librarySystem.getStudentRentalHistory(student);
            
            int totalRentals = history.size();
            int notReturned = 0;
            int delayed = 0;
            
            for (RentalRequest request : history) {
                if (!request.isReturned()) notReturned++;
                if (request.isDelayed()) delayed++;
            }
            
            System.out.println("\nStudent: " + student.getName() + 
                             " (ID: " + student.getStudentId() + 
                             ", Status: " + (student.isActive() ? "Active" : "Inactive") + ")");
            System.out.println("  Total rentals: " + totalRentals);
            System.out.println("  Not returned: " + notReturned);
            System.out.println("  Delayed: " + delayed);
        }
        
        // Top 10 students with most delays
        System.out.println("\n=== Top 10 Students with Most Delays ===");
        // Simplified for Phase 1 - would sort and display top 10 in real system
        System.out.println("(Feature to be implemented in Phase 2)");
    }
    
    private static int getIntInput() {
        try {
            int input = Integer.parseInt(scanner.nextLine().trim());
            return input;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

