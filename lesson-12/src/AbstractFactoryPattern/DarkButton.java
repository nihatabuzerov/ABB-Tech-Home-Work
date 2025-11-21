package AbstractFactoryPattern;

public class DarkButton implements Button {

    @Override
    public void click() {
        System.out.println("Dark Button klik olundu");
    }
}

