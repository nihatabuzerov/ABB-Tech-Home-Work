import org.example.Book;
import org.example.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class LibraryAnalysisTest {

    @Test
    void testAverageRating () {

        List<Book> books = Arrays.asList(
                new Book("Book1", "Author", 2020, 4.5, true),
                new Book("Book2", "Author", 2020, 5.0, true)
        );

        double avg = books
                .stream()
                .mapToDouble(Book::getRating)
                .average()
                .orElse(0.0);

        assertEquals(4.75, avg, 0.001);
    }

    @Test
    void testAvailableBooksAfter2000 () {

        List<Book> books = Arrays.asList(
                new Book("Old", "Author", 1999, 4.5, true),
                new Book("New", "Author", 2020, 4.5, true),
                new Book("Unavailable", "Author", 2021, 4.5, false)
        );

        List<Book> result = books
                .stream()
                .filter(b -> b.getYear() > 2000 && b.isAvailable())
                .toList();

        assertEquals(1, result.size());
        assertEquals("New", result.get(0).getTitle());
    }
}