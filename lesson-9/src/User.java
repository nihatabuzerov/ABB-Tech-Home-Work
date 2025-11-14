import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private int age;
    private List<BorrowRecord> borrowHistory;

    public User(String name, int age, List<BorrowRecord> borrowHistory) {
        this.name = name;
        this.age = age;
        this.borrowHistory = borrowHistory != null ? borrowHistory : new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<BorrowRecord> getBorrowHistory() {
        return borrowHistory;
    }

    public void addBorrowRecord(BorrowRecord record) {
        borrowHistory.add(record);
    }

    @Override
    public String toString() {
        return name + " (" + age + " y.o.)";
    }
}
