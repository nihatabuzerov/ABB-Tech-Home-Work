public class Passenger {
    private String name;
    private boolean isPriority;

    public Passenger(String name, boolean isPriority) {
        this.name = name;
        this.isPriority = isPriority;
    }

    public boolean isPriority() {
        return isPriority;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return isPriority ? name + "(PRIORITY)" : name;
    }
}
