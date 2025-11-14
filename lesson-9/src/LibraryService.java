import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {

    private List<Book> books;
    private List<User> users;

    public LibraryService(List<Book> books, List<User> users) {
        this.books = books;
        this.users = users;
    }

    public void sortBooks() {
        System.out.println("\n Sorted Books:");

        books.stream()
                .sorted(
                        Comparator.comparing(Book::getRating).reversed()
                                .thenComparing(Book::getYear)
                                .thenComparing(Book::getTitle)
                )
                .forEach(System.out::println);
    }


    public void analyzeLibrary() {
        System.out.println("\n Library Analysis:");

        // 1. Average rating
        double avgRating = books.stream()
                .mapToDouble(Book::getRating)
                .average()
                .orElse(0.0);

        System.out.println("Average Rating: " + avgRating);

        List<Book> availableAfter2000 = books.stream()
                .filter(b -> b.getYear() > 2000 && b.isAvailable())
                .collect(Collectors.toList());

        System.out.println("Available after 2000: " + availableAfter2000);

        String mostBorrowed = users.stream()
                .flatMap(u -> u.getBorrowHistory().stream())
                .collect(Collectors.groupingBy(
                        r -> r.getBook().getTitle(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> e.getKey() + " (" + e.getValue() + " times)")
                .orElse("No data");

        System.out.println("Most borrowed book: " + mostBorrowed);

        System.out.println("Currently reading:");
        Map<String, List<Book>> currentReads =
                users.stream().collect(Collectors.toMap(
                        User::getName,
                        u -> u.getBorrowHistory().stream()
                                .filter(r -> r.getReturnedDate() == null)
                                .map(BorrowRecord::getBook)
                                .collect(Collectors.toList())
                ));

        currentReads.forEach((user, list) ->
                System.out.println(user + " -> " + list)
        );

        Map<String, List<Book>> booksByAuthor =
                books.stream().collect(Collectors.groupingBy(
                        Book::getAuthor,
                        Collectors.filtering(
                                b -> b.getYear() > 1950,
                                Collectors.toList()
                        )
                ));

        System.out.println("Books grouped by author (after 1950):");
        booksByAuthor.forEach((author, list) ->
                System.out.println(author + " -> " + list)
        );
    }


    public void uniqueAuthorsRead() {
        System.out.println("\n Authors read by users:");

        Set<String> authors = users.stream()
                .flatMap(u -> u.getBorrowHistory().stream())
                .map(r -> r.getBook().getAuthor())
                .collect(Collectors.toSet());

        System.out.println(authors);
    }


    public Optional<Book> findRecommendedBookForUser(User user) {

        if (user.getBorrowHistory().isEmpty())
            return Optional.empty();

        Optional<String> topAuthor =
                user.getBorrowHistory().stream()
                        .collect(Collectors.groupingBy(
                                r -> r.getBook().getAuthor(),
                                Collectors.counting()
                        ))
                        .entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey);

        if (topAuthor.isEmpty())
            return Optional.empty();

        String author = topAuthor.get();

        return books.stream()
                .filter(b -> b.getAuthor().equals(author))
                .max(Comparator.comparingDouble(Book::getRating));
    }


    public Optional<User> findTopReaderOfMonth(int month, int year) {

        return users.stream()
                .max(Comparator.comparingLong(u ->
                        u.getBorrowHistory().stream()
                                .filter(r ->
                                        r.getBorrowedDate().getMonthValue() == month &&
                                                r.getBorrowedDate().getYear() == year
                                )
                                .count()
                ));
    }
}
