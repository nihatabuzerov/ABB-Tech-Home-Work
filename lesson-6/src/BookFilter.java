
@FunctionalInterface
public interface BookFilter {
    boolean filter(Library<?>.Book book);
}
