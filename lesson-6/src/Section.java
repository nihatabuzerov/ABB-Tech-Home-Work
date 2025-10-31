import java.util.ArrayList;
import java.util.List;

public class Section {

    private final String name;
    private final List<Library<?>.Book> books = new ArrayList<>();

    public Section(String name) {
        this.name = name;
    }

    public void addBook(Library<?>.Book book) {
        books.add(book);
    }

    public void displayBooks() {
        for (Library<?>.Book book : books) {
            System.out.println(" - " + book);
        }
    }

    public String getName() {
        return name;
    }

    public List<Library<?>.Book> getBooks() {
        return books;
    }
}