package BuilderPattern;

public class Main {
    public static void main(String[] args) {

        User user = User.builder()
                .name("Nihat")
                .surname("Abuzerov")
                .age(25)
                .city("Baku")
                .build();

        System.out.println("Name: " + user.getName());
        System.out.println("Surname: " + user.getSurname());
        System.out.println("Age: " + user.getAge());
        System.out.println("City: " + user.getCity());
    }
}
