package org.example;

import java.time.LocalDate;

@SuppressWarnings("ClassCanBeRecord")
public class BorrowRecord {

    private final Book book;
    private final LocalDate borrowedDate;
    private final LocalDate returnedDate;

    public BorrowRecord(
            Book book,
            LocalDate borrowedDate,
            LocalDate returnedDate
    ) {
        this.book = book;
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
    }

    public Book getBook () {
        return book;
    }

    public LocalDate getBorrowedDate () {
        return borrowedDate;
    }

    public LocalDate getReturnedDate () {
        return returnedDate;
    }

    public boolean isReturned () {
        return returnedDate != null;
    }
}