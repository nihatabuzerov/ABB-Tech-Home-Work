public class Car {
    int id;
    String brand;
    String model;
    int year;


    public Car( int id,String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;
        return id == car.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
