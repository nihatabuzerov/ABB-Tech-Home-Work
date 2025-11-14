import java.time.LocalDate;

public class BorrowRecord {
    private Book book;
    private LocalDate borrowedDate;
    private LocalDate returnedDate;

    public BorrowRecord(Book book, LocalDate borrowedDate, LocalDate returnedDate) {
        this.book = book;
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public boolean isReturned() {
        return returnedDate != null;
    }

    @Override
    public String toString() {
        return book.getTitle() + " borrowed at " + borrowedDate +
                (returnedDate == null ? " (not returned)" : " returned at " + returnedDate);
    }
}
