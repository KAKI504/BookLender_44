package lesson44;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String id;
    private String name;
    private String email;
    private List<Book> currentBooks;
    private List<Book> borrowHistory;

    public Employee(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.currentBooks = new ArrayList<>();
        this.borrowHistory = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Book> getCurrentBooks() {
        return currentBooks;
    }

    public List<Book> getBorrowHistory() {
        return borrowHistory;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCurrentBooks(List<Book> currentBooks) {
        this.currentBooks = currentBooks;
    }

    public void setBorrowHistory(List<Book> borrowHistory) {
        this.borrowHistory = borrowHistory;
    }

    public boolean canBorrowBooks() {
        return currentBooks.size() < 2;
    }

    public void borrowBook(Book book) {
        if (canBorrowBooks()) {
            currentBooks.add(book);
            book.borrowBook(this.name);
        }
    }

    public void returnBook(Book book) {
        if (currentBooks.remove(book)) {
            borrowHistory.add(book);
            book.returnBook();
        }
    }

    public int getCurrentBooksCount() {
        return currentBooks.size();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", currentBooks=" + currentBooks.size() +
                ", borrowHistory=" + borrowHistory.size() +
                '}';
    }
}
