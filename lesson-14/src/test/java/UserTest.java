import org.example.Book;
import org.example.BorrowRecord;
import org.example.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserWithBorrowHistory () {

        Book book = new Book("1984", "George Orwell", 1949, 4.9, true);
        BorrowRecord record = new BorrowRecord(book, LocalDate.now(), null);

        User user = new User("Ali", List.of(record));

        assertEquals("Ali", user.getName());
        assertEquals(1, user.getBorrowHistory().size());
        assertEquals(book, user.getBorrowHistory().getFirst().getBook());
    }

    @Test
    void testUserWithEmptyHistory () {

        User user = new User("Empty User", List.of());

        assertEquals("Empty User", user.getName());
        assertTrue(user.getBorrowHistory().isEmpty());
    }

    @Test
    void testUserWithMultipleBorrows () {

        Book book1 = new Book("Book1", "Author1", 2020, 4.5, true);
        Book book2 = new Book("Book2", "Author2", 2021, 4.7, true);
        Book book3 = new Book("Book3", "Author3", 2022, 4.9, true);

        BorrowRecord record1 = new BorrowRecord(book1, LocalDate.now(), null);
        BorrowRecord record2 = new BorrowRecord(book2, LocalDate.now(), null);
        BorrowRecord record3 = new BorrowRecord(book3, LocalDate.now(), LocalDate.now());

        User user = new User("Ali", List.of(record1, record2, record3));

        assertEquals(3, user.getBorrowHistory().size());
        assertEquals("Ali", user.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = { "Ali", "Leyla", "Murad", "John Doe", "A" })
    void testUserNames (String name) {

        User user = new User(name, List.of());

        assertEquals(name, user.getName());
        assertNotNull(user.getBorrowHistory());
    }

    @Test
    void testUserBorrowHistoryCount () {

        Book book1 = new Book("Book1", "Author", 2020, 4.5, true);
        Book book2 = new Book("Book2", "Author", 2020, 4.5, true);

        User user = new User("Ali", List.of(

                new BorrowRecord(book1, LocalDate.of(2025, 1, 1), null),

                new BorrowRecord(
                        book2,
                        LocalDate.of(2025, 1, 5),
                        LocalDate.of(2025, 1, 15)
                )
        ));

        assertEquals(2, user.getBorrowHistory().size());

        long unreturned = user.getBorrowHistory()
                .stream()
                .filter(r -> !r.isReturned())
                .count();

        assertEquals(1, unreturned);
    }

    @Test
    void testUserCanHaveSameBookMultipleTimes () {

        Book book = new Book("Popular Book", "Author", 2020, 4.5, true);

        User user = new User("Ali", List.of(
                new BorrowRecord(
                        book,
                        LocalDate.of(2025, 1, 1),
                        LocalDate.of(2025, 1, 15)
                ),

                new BorrowRecord(book, LocalDate.of(2025, 2, 1), null)
        ));

        assertEquals(2, user.getBorrowHistory().size());
        assertEquals(book, user.getBorrowHistory().get(0).getBook());
        assertEquals(book, user.getBorrowHistory().get(1).getBook());
    }
}