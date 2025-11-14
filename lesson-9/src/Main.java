import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // --- Books ---
        Book b1 = new Book("1984", "George Orwell", 1949, 4.9, true);
        Book b2 = new Book("Animal Farm", "George Orwell", 1945, 4.8, false);
        Book b3 = new Book("Clean Code", "Robert Martin", 2008, 4.7, true);
        Book b4 = new Book("Effective Java", "Joshua Bloch", 2018, 4.9, true);
        Book b5 = new Book("The Pragmatic Programmer", "Andy Hunt", 1999, 4.6, true);
        Book b6 = new Book("Java Concurrency in Practice", "Brian Goetz", 2006, 4.5, false);

        List<Book> books = Arrays.asList(b1, b2, b3, b4, b5, b6);

        // --- Users ---
        User u1 = new User("Aydin", 25, Arrays.asList(
                new BorrowRecord(b1, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 10)),
                new BorrowRecord(b3, LocalDate.of(2025, 10, 5), null)
        ));

        User u2 = new User("Leyla", 22, Arrays.asList(
                new BorrowRecord(b4, LocalDate.of(2025, 10, 2), LocalDate.of(2025, 10, 20)),
                new BorrowRecord(b6, LocalDate.of(2025, 10, 12), null)
        ));

        User u3 = new User("Murad", 28, Arrays.asList(
                new BorrowRecord(b5, LocalDate.of(2025, 9, 10), LocalDate.of(2025, 9, 25))
        ));

        List<User> users = Arrays.asList(u1, u2, u3);

        // --- Library Service ---
        LibraryService service = new LibraryService(books, users);

        // Sort books
        service.sortBooks();

        // Library analysis
        service.analyzeLibrary();

        // Unique authors read
        service.uniqueAuthorsRead();

        // Recommendation
        System.out.println("\n Recommendation for Aydin:");
        service.findRecommendedBookForUser(u1)
                .ifPresentOrElse(
                        b -> System.out.println("Recommended: " + b),
                        () -> System.out.println("No recommendation available.")
                );

        // Top Reader of October 2025
        System.out.println("\n Top Reader of October 2025:");
        service.findTopReaderOfMonth(10, 2025)
                .ifPresentOrElse(
                        u -> System.out.println("Top Reader: " + u.getName()),
                        () -> System.out.println("No reader found.")
                );

        // Bonus: Reading durations
        System.out.println("\n Reading Durations:");
        users.stream()
                .flatMap(u -> u.getBorrowHistory().stream())
                .filter(BorrowRecord::isReturned)
                .forEach(r -> {
                    long days = java.time.Duration.between(
                            r.getBorrowedDate().atStartOfDay(),
                            r.getReturnedDate().atStartOfDay()
                    ).toDays();
                    System.out.println(r.getBook().getTitle() + " read in " + days + " days");
                });
    }
}
