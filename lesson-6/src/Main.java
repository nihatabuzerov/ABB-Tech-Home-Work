
public class Main {
    public static void main(String[] args) {

        Library<Section> library = new Library<>();

        Section fiction = new Section("Fiction");
        Section science = new Section("Science");

        Library<Section>.Book b1 = library.new Book("The Martian", "Andy Weir", 2011);
        Library<Section>.Book b2 = library.new Book("Project Hail Mary", "Andy Weir", 2021);
        Library<Section>.Book b3 = library.new Book("A Brief History of Time", "Stephen Hawking", 1988);

        fiction.addBook(b1);
        fiction.addBook(b2);
        science.addBook(b3);

        library.addSection(fiction);
        library.addSection(science);

        library.displayAllSections();

        library.filterBooks(book -> book.getYear() > 2015);
    }
}
