package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {

    static void main () {

        Book b1 = new Book("1984", "George Orwell", 1949, 4.9, true);
        Book b2 = new Book("Animal Farm", "George Orwell", 1945, 4.8, false);
        Book b3 = new Book("Clean Code", "Robert Martin", 2008, 4.7, true);
        Book b4 = new Book("Effective Java", "Joshua Bloch", 2018, 4.9, true);
        Book b5 = new Book("The Pragmatic Programmer", "Andy Hunt", 1999, 4.6, true);
        Book b6 = new Book("Java Concurrency in Practice", "Brian Getz", 2006, 4.5, false);

        List<Book> books = Arrays.asList(b1, b2, b3, b4, b5, b6);

        User u1 = new User("Aydin", Arrays.asList(
                new BorrowRecord(b1, LocalDate.of(2025, 9, 1),
                        LocalDate.of(2025, 9, 10)),
                new BorrowRecord(b3, LocalDate.of(2025, 10, 5), null)
        ));

        User u2 = new User("Leyla", Arrays.asList(
                new BorrowRecord(b4, LocalDate.of(2025, 10, 2),
                        LocalDate.of(2025, 10, 20)),
                new BorrowRecord(b6, LocalDate.of(2025, 10, 12), null)
        ));

        User u3 = new User("Murad", List.of(
                new BorrowRecord(b5, LocalDate.of(2025, 9, 10),
                        LocalDate.of(2025, 9, 25))
        ));

        List<User> users = Arrays.asList(u1, u2, u3);

        Library service = new Library(books, users);

        service.sortBooks();
        service.analyzeLibrary();
        service.uniqueAuthorsRead();

        System.out.println("\nRecommendation for Aydin:");
        service.findRecommendedBookForUser(u1)
                .ifPresentOrElse(
                        b -> System.out.println("Recommended: " + b.getTitle()),
                        () -> System.out.println("No recommendation available.")
                );

        System.out.println("\nTop Reader of October 2025:");
        service.findTopReaderOfMonth(10, 2025)
                .ifPresentOrElse(
                        u -> System.out.println("Top Reader: " + u.getName()),
                        () -> System.out.println("No reader found.")
                );

        System.out.println("\nReading Durations:");
        users.stream()
                .flatMap(u -> u.getBorrowHistory().stream())
                .filter(BorrowRecord::isReturned)
                .forEach(r -> {
                    long days = Duration.between(
                            r.getBorrowedDate().atStartOfDay(),
                            r.getReturnedDate().atStartOfDay()
                    ).toDays();

                    System.out.println(r.getBook().getTitle() + " read in " + days + " days");
                });
    }
}
