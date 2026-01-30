package org.example;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("ClassCanBeRecord")
public class Library {

    private final List<Book> books;
    private final List<User> users;

    public Library (
            List<Book> books,
            List<User> users
    ) {
        this.books = books;
        this.users = users;
    }

    public Optional<Book> findRecommendedBookForUser (User user) {
        if (user.getBorrowHistory().isEmpty()) {
            return Optional.empty();
        }

        Map<String, Long> authorCounts = user.getBorrowHistory()
                .stream()
                .map(r -> r.getBook().getAuthor())
                .collect(Collectors.groupingBy(
                        author -> author,
                        Collectors.counting()
                ));

        Optional<String> favoriteAuthor = authorCounts.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);

        return favoriteAuthor.flatMap(author ->
                books.stream()
                        .filter(b -> b.getAuthor().equals(author))
                        .filter(Book::isAvailable)
                        .max(Comparator.comparing(Book::getRating))
        );
    }

    public Optional<User> findTopReaderOfMonth (int month, int year) {
        Map<User, Long> borrowCountsByUser = users.stream()
                .collect(Collectors.toMap(
                        user -> user,
                        user -> user.getBorrowHistory().stream()
                                .filter(r -> {
                                    LocalDate borrowed = r.getBorrowedDate();
                                    return borrowed.getMonthValue() == month
                                            && borrowed.getYear() == year;
                                })
                                .count()
                ));

        return borrowCountsByUser.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 0)
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    public void sortBooks () {
        this.books.sort(
                Comparator.comparing(Book::getRating)
                        .reversed()
                        .thenComparing(Book::getYear)
                        .thenComparing(Book::getTitle)
        );

        System.out.println("Sorted Books: ");

        this.books.forEach(b -> {
            String title = b.getTitle();
            String author = b.getAuthor();
            int year = b.getYear();
            double rating = b.getRating();

            System.out.println(title + " (" + author + ", " + year + ") Rating: " + rating);
        });
    }

    public void analyzeLibrary () {

        double avgRating = this.books
                .stream()
                .mapToDouble(Book::getRating)
                .average()
                .orElse(0.0);

        System.out.println("Average Rating: " + avgRating);

        List<Book> availableAfter2000 = this.books
                .stream()
                .filter(b -> b.getYear() > 2000 && b.isAvailable())
                .toList();

        System.out.println("Available after 2000: " + availableAfter2000);

        Map<String, Long> bookCounts = users
                .stream()
                .flatMap(u -> u.getBorrowHistory().stream())
                .collect(Collectors.groupingBy(
                        r -> r.getBook().getTitle(),
                        Collectors.counting()
                ));

        Optional<Map.Entry<String, Long>> mostBorrowed = bookCounts
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        mostBorrowed.ifPresent(entry -> {
            String bookTitle = entry.getKey();
            Long borrowCount = entry.getValue();
            System.out.println("Most borrowed book: " + bookTitle + " (" + borrowCount + " times)");
        });

        Map<String, List<Book>> currentlyReading = new HashMap<>();

        for (User user : users) {
            List<Book> unreturnedBooks = user.getBorrowHistory()
                    .stream()
                    .filter(r -> r.getReturnedDate() == null)
                    .map(BorrowRecord::getBook)
                    .collect(Collectors.toList());

            currentlyReading.put(user.getName(), unreturnedBooks);
        }

        System.out.println("Currently reading: ");
        currentlyReading.forEach(
                (userName, bookList) -> System.out.println(userName + " -> " + bookList)
        );

        Map<String, List<Book>> booksByAuthor = books
                .stream()
                .collect(Collectors.groupingBy(
                        Book::getAuthor,
                        Collectors.filtering(b -> b.getYear() > 1950, Collectors.toList())
                ));

        booksByAuthor.entrySet().removeIf(entry -> entry.getValue().isEmpty());

        System.out.println("Books grouped by author (after 1950):");
        booksByAuthor.forEach(
                (author, bookList) -> System.out.println(author + " -> " + bookList)
        );
    }

    public void uniqueAuthorsRead () {

        Set<String> authors = users.stream()
                .flatMap(u -> u.getBorrowHistory().stream())
                .map(r -> r.getBook().getAuthor())
                .collect(Collectors.toSet());

        System.out.println("Unique Authors: " + authors);
    }
}