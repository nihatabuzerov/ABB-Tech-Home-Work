import org.example.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testBookCreation () {

        String expectedTitle = "1984";
        String expectedAuthor = "George Orwell";
        int expectedYear = 1949;
        double expectedRating = 4.9;
        boolean expectedAvailability = true;

        Book book = new Book(expectedTitle, expectedAuthor, expectedYear, expectedRating, expectedAvailability);

        String actualTitle = book.getTitle();
        String actualAuthor = book.getAuthor();
        int actualYear = book.getYear();
        double actualRating = book.getRating();
        boolean actualAvailability = book.isAvailable();

        assertEquals(expectedTitle, actualTitle);
        assertEquals(expectedAuthor, actualAuthor);
        assertEquals(expectedYear, actualYear);
        assertEquals(expectedRating, actualRating);
        assertEquals(expectedAvailability, actualAvailability);
    }

    @ParameterizedTest
    @CsvSource({
            "1984, George Orwell, 1949, 4.9, true",
            "Clean Code, Robert Martin, 2008, 4.7, false",
            "Effective Java, Joshua Bloch, 2018, 4.9, true"
    })
    void testMultipleBooks (
            String expectedTitle,
            String expectedAuthor,
            int expectedYear,
            double expectedRating,
            boolean expectedAvailability
    ) {

        Book book = new Book(
                expectedTitle,
                expectedAuthor,
                expectedYear,
                expectedRating,
                expectedAvailability
        );

        String actualTitle = book.getTitle();
        String actualAuthor = book.getAuthor();
        int actualYear = book.getYear();
        double actualRating = book.getRating();
        boolean actualAvailability = book.isAvailable();

        assertEquals(expectedTitle, actualTitle);
        assertEquals(expectedAuthor, actualAuthor);
        assertEquals(expectedYear, actualYear);
        assertEquals(expectedRating, actualRating);
        assertEquals(expectedAvailability, actualAvailability);
    }

    @Test
    void testToString () {

        String title = "Clean Code";
        String author = "Robert Martin";
        int year = 2008;
        double rating = 4.7;
        boolean availability = true;

        Book book = new Book(title, author, year, rating, availability);

        String actualResult = book.toString();

        assertTrue(actualResult.contains("Clean Code"));
        assertTrue(actualResult.contains("Robert Martin"));
        assertTrue(actualResult.contains("2008"));
        assertTrue(actualResult.contains("4.7"));
    }

    @Test
    void testBookAvailability () {

        Book availableBook = new Book("Book1", "Author", 2020, 4.5, true);
        Book unavailableBook = new Book("Book2", "Author", 2020, 4.5, false);

        boolean actualAvailableStatus = availableBook.isAvailable();
        boolean actualUnavailableStatus = unavailableBook.isAvailable();

        assertTrue(actualAvailableStatus);
        assertFalse(actualUnavailableStatus);
    }

    @Test
    void testBookRatingRange () {

        double expectedLowRating = 1.0;
        double expectedHighRating = 5.0;

        Book lowRatedBook = new Book("Low", "Author", 2020, expectedLowRating, true);
        Book highRatedBook = new Book("High", "Author", 2020, expectedHighRating, true);

        double actualLowRating = lowRatedBook.getRating();
        double actualHighRating = highRatedBook.getRating();

        assertEquals(expectedLowRating, actualLowRating);
        assertEquals(expectedHighRating, actualHighRating);
    }
}