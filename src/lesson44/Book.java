package lesson44;

public class Book {
    private String id;
    private String title;
    private String author;
    private String image;
    private String status;
    private boolean isBorrowed;
    private String borrowedBy;

    public Book(String id, String title, String author, String image) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.image = image;
        this.status = "available";
        this.isBorrowed = false;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getStatus() {
        return status;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }

    public void borrowBook(String employeeName) {
        this.isBorrowed = true;
        this.borrowedBy = employeeName;
        this.status = "borrowed";
    }

    public void returnBook() {
        this.isBorrowed = false;
        this.borrowedBy = null;
        this.status = "available";
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", status='" + status + '\'' +
                ", borrowedBy='" + borrowedBy + '\'' +
                '}';
    }
}
