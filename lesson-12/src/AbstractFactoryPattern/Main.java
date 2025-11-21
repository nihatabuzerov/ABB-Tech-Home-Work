package AbstractFactoryPattern;

public class Main {
    public static void main(String[] args) {

        ButtonFactory factory = new LightButtonFactory();

        Button button = factory.createButton();
        button.click();
    }
}
