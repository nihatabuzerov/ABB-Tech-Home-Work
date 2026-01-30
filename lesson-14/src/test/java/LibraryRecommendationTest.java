import org.example.Book;
import org.example.Library;
import org.example.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LibraryRecommendationTest {



    @Test
    void testEmptyBorrowHistory () {

        Book expectedBook = new Book("Book1", "Author", 2020, 4.5, true);
        List<Book> books = List.of(expectedBook);

        User user = new User("User", List.of());
        List<User> users = List.of(user);

        Library library = new Library(books, users);

        Optional<Book> actualRecommendation = library.findRecommendedBookForUser(user);

        assertFalse(actualRecommendation.isPresent());
    }

    @ParameterizedTest
    @MethodSource("provideSourceForScenarios")
    void testRecommendationScenarios (
            List<Book> books,
            User user,
            boolean shouldHaveRecommendation,
            String expectedAuthor
    ) {

        Library library = new Library(books, List.of(user));

        Optional<Book> actualRecommendation = library.findRecommendedBookForUser(user);

        assertEquals(shouldHaveRecommendation, actualRecommendation.isPresent());

        if (shouldHaveRecommendation && expectedAuthor != null) {
            String actualAuthor = actualRecommendation.get().getAuthor();
            assertEquals(expectedAuthor, actualAuthor);
        }
    }
}