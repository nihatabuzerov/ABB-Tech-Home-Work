import org.example.Book;
import org.example.BorrowRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RecordTest {

    @Test
    void testRecordWithReturnDate () {

        Book expectedBook = new Book("Test Book", "Test Author", 2020, 4.5, true);
        LocalDate expectedBorrowedDate = LocalDate.of(2025, 1, 1);
        LocalDate expectedReturnedDate = LocalDate.of(2025, 1, 15);

        BorrowRecord record = new BorrowRecord(expectedBook, expectedBorrowedDate, expectedReturnedDate);

        Book actualBook = record.getBook();
        LocalDate actualBorrowedDate = record.getBorrowedDate();
        LocalDate actualReturnedDate = record.getReturnedDate();
        boolean actualReturnedStatus = record.isReturned();

        assertEquals(expectedBook, actualBook);
        assertEquals(expectedBorrowedDate, actualBorrowedDate);
        assertEquals(expectedReturnedDate, actualReturnedDate);
        assertTrue(actualReturnedStatus);
    }

    @Test
    void testRecordWithoutReturnDate () {

        Book expectedBook = new Book("Test Book", "Test Author", 2020, 4.5, true);
        LocalDate expectedBorrowedDate = LocalDate.now();

        BorrowRecord record = new BorrowRecord(expectedBook, expectedBorrowedDate, null);

        Book actualBook = record.getBook();
        LocalDate actualBorrowedDate = record.getBorrowedDate();
        LocalDate actualReturnedDate = record.getReturnedDate();
        boolean actualReturnedStatus = record.isReturned();

        assertEquals(expectedBook, actualBook);
        assertEquals(expectedBorrowedDate, actualBorrowedDate);
        assertNull(actualReturnedDate);
        assertFalse(actualReturnedStatus);
    }

    @ParameterizedTest
    @CsvSource({
            "2025, 1, 1, 2025, 1, 15, true",
            "2025, 2, 1, 2025, 2, 28, true",
            "2025, 6, 15, 2025, 7, 1, true"
    })
    void testMultipleRecords (
            int borrowYear, int borrowMonth, int borrowDay,
            int returnYear, int returnMonth, int returnDay,
            boolean expectedReturnedStatus
    ) {

        Book book = new Book("Book", "Author", 2020, 4.5, true);
        LocalDate borrowedDate = LocalDate.of(borrowYear, borrowMonth, borrowDay);
        LocalDate returnedDate = LocalDate.of(returnYear, returnMonth, returnDay);

        BorrowRecord record = new BorrowRecord(book, borrowedDate, returnedDate);

        boolean actualReturnedStatus = record.isReturned();
        LocalDate actualReturnedDate = record.getReturnedDate();

        assertEquals(expectedReturnedStatus, actualReturnedStatus);
        assertNotNull(actualReturnedDate);
    }

    @Test
    void testBorrowedDateBeforeReturnedDate () {

        Book book = new Book("Book", "Author", 2020, 4.5, true);
        LocalDate borrowedDate = LocalDate.of(2025, 1, 1);
        LocalDate returnedDate = LocalDate.of(2025, 1, 15);

        BorrowRecord record = new BorrowRecord(book, borrowedDate, returnedDate);

        LocalDate actualBorrowedDate = record.getBorrowedDate();
        LocalDate actualReturnedDate = record.getReturnedDate();

        assertTrue(actualBorrowedDate.isBefore(actualReturnedDate));
    }

    @Test
    void testSameDayBorrowAndReturn () {

        Book book = new Book("Book", "Author", 2020, 4.5, true);
        LocalDate expectedDate = LocalDate.of(2025, 1, 1);

        BorrowRecord record = new BorrowRecord(book, expectedDate, expectedDate);

        boolean actualReturnedStatus = record.isReturned();
        LocalDate actualBorrowedDate = record.getBorrowedDate();
        LocalDate actualReturnedDate = record.getReturnedDate();

        assertTrue(actualReturnedStatus);
        assertEquals(expectedDate, actualBorrowedDate);
        assertEquals(expectedDate, actualReturnedDate);
    }
}