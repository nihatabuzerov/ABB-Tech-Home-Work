package org.example;

import java.util.List;

@SuppressWarnings("ClassCanBeRecord")
public class User {

    private final String name;
    private final List<BorrowRecord> borrowHistory;

    public User (
            String name,
            List<BorrowRecord> borrowHistory
    ) {
        this.name = name;
        this.borrowHistory = borrowHistory;
    }

    public String getName () {
        return name;
    }

    public List<BorrowRecord> getBorrowHistory () {
        return borrowHistory;
    }
}