import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private int year;
    private double rating; // 0.0 - 5.0
    private boolean isAvailable;

    public Book(String title, String author, int year, double rating, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.rating = rating;
        this.isAvailable = isAvailable;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public double getRating() {
        return rating;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return title + " (" + author + ", " + year + ") " + rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
