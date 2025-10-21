package BookManagment;

public class Book {
    private String title;
    private String author;
    private String isbn;
    protected int totalCopies;
    protected int availableCopies;


    {
        System.out.println("New book created!");
    }

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.totalCopies = 1;
        this.availableCopies = 1;
    }

    public void borrowBook() {
        if (availableCopies > 0) {
            availableCopies--;
            System.out.println("Book borrowed successfully.");
        } else {
            System.out.println("Sorry, no copies available.");
        }
    }

    public void returnBook() {
        if (availableCopies < totalCopies) {
            availableCopies++;
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("All copies are already in the library.");
        }
    }

    public void printInfo() {
        System.out.println("Book Info:");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("ISBN: " + isbn);
        System.out.println("Total Copies: " + totalCopies);
        System.out.println("Available Copies: " + availableCopies);
    }

    public static void libraryRules() {
        System.out.println("Max 3 books can be borrowed per person.");
    }

    public final void bookType() {
        System.out.println("This is a regular book.");
    }
}

