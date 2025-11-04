interface Animal{
    void sound();
}


public class AnonymousPractice {

    public static void main(String[] args) {
        Animal cat = new Animal() {
            @Override
            public void sound() {
                System.out.println("miyau");
            }
        };
        cat.sound();
    }
}
