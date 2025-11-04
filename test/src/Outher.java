public class Outher {
    private String message = "Salam!";

    class Inner {
        public void printMessage() {
            System.out.println(message); // Outer class-ın field-ına giriş var
        }
    }
}