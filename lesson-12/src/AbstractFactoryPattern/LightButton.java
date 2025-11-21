package AbstractFactoryPattern;

public class LightButton implements Button {

    @Override
    public void click() {
        System.out.println("Light Button klik olundu");
    }
}
