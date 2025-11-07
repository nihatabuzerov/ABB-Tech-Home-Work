import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class CarRentalSystem {

    Set<Car> allCars = new HashSet<>();
    Set<Car> availableCars = new HashSet<>();
    Map<Customer, Car> rentedCars = new HashMap<>();
    Map<Car, LocalDateTime> rentalTimes = new HashMap<>();
    Map<Car, LocalDateTime> history = new HashMap<>();

    public void addCar(Car car) {
        allCars.add(car);
        availableCars.add(car);
    }

    public void rentCar(Customer c, Car car, LocalDateTime customTime) {
        if (!availableCars.contains(car)) {
            System.out.println(" Car is not available!");
            return;
        }

        rentedCars.put(c, car);
        rentalTimes.put(car, customTime);
        availableCars.remove(car);

        System.out.println(" " + c.getName() + " rented " + car.getModel() + " at " + customTime);
    }

    public void returnCar(Customer c, LocalDateTime returnTime) {
        if (!rentedCars.containsKey(c)) {
            System.out.println(" This customer has no active rentals!");
            return;
        }

        Car car = rentedCars.get(c);
        LocalDateTime start = rentalTimes.get(car);
        LocalDateTime end = returnTime;

        Duration duration = Duration.between(start, end);
        long hours = duration.toHours();
        long days = hours / 24;

        System.out.println("\n " + c.getName() + " returned " + car.getModel() +
                " after " + days + " days (" + hours + " hours total)");

        availableCars.add(car);
        rentedCars.remove(c);
        history.put(car, end);
    }

    public void printActiveRentals() {
        System.out.println("\n Active Rentals:");
        for (Map.Entry<Customer, Car> entry : rentedCars.entrySet()) {
            Car car = entry.getValue();
            LocalDateTime time = rentalTimes.get(car);
            System.out.println(entry.getKey().getName() + " -> " + car.getModel() +
                    " (rented at " + time + ")");
        }
    }

    public void printAvailableCars() {
        System.out.println("\n Available Cars:");
        for (Car car : availableCars) {
            System.out.println(car);
        }
    }

    public void printRentalHistory() {
        System.out.println("\n Rental History:");
        for (Map.Entry<Car, LocalDateTime> entry : history.entrySet()) {
            System.out.println(entry.getKey().getModel() +
                    " was last returned at " + entry.getValue());
        }
    }
}
