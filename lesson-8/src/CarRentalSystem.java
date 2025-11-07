import java.util.*;

public class CarRentalSystem {

    private List<Car> availableCars = new ArrayList<>();
    private Map<Customer, Car> activeRentals = new HashMap<>();
    private List<String> rentalHistory = new ArrayList<>();


    public void addCar(Car car) {
        availableCars.add(car);
    }

    public void rentCar(Customer customer, Car car) {


        if (activeRentals.containsValue(car)) {
            System.out.println(car.model + " artıq icarədədir!");
            return;
        }

        if (availableCars.remove(car)) {
            activeRentals.put(customer, car);
            System.out.println(customer.getName() + " rented " + car.brand + " " + car.model);
        } else {
            System.out.println("Bu maşın mövcud deyil!");
        }
    }

    public void returnCar(Customer customer) {
        if (activeRentals.containsKey(customer)) {
            Car returnedCar = activeRentals.remove(customer);
            availableCars.add(returnedCar);
            rentalHistory.add(customer.getName() + " returned " + returnedCar.brand + " " + returnedCar.model);
            System.out.println(customer.getName() + " maşını geri qaytardı.");
        } else {
            System.out.println(customer.getName() + " hal-hazırda heç bir maşın icarə etmir.");
        }
    }

    public void printActiveRentals() {
        System.out.println("\n--- Active Rentals ---");
        if (activeRentals.isEmpty()) {
            System.out.println("Hazırda icarədə maşın yoxdur.");
            return;
        }
        activeRentals.forEach((customer, car) -> {
            System.out.println(customer.getName() + " has rented " + car.brand + " " + car.model);
        });
    }

    public void printAvailableCars() {
        System.out.println("\n--- Available Cars ---");
        if (availableCars.isEmpty()) {
            System.out.println("Mövcud maşın yoxdur.");
            return;
        }
        for (Car car : availableCars) {
            System.out.println(car.brand + " " + car.model + " (" + car.year + ")");
        }
    }

    public void printRentalHistory() {
        System.out.println("\n--- Rental History ---");
        if (rentalHistory.isEmpty()) {
            System.out.println("Hələ tarix yoxdur.");
            return;
        }
        for (String record : rentalHistory) {
            System.out.println(record);
        }
    }
}
