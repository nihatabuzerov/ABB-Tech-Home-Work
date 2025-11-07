import java.time.LocalDateTime;
import java.util.*;

public class CarRentalSystem {

        Set<Car> allCars = new HashSet<>();
        Map<Customer,Car> activeRentals = new HashMap<>();
        Map<Car, LocalDateTime> rentTimes = new HashMap<>();
        Set<Car> availableCars = new HashSet<>();
        List<String> rentalHistory = new ArrayList<>();



        public void addCar(Car car){
            allCars.add(car);
            availableCars.add(car);
        }

        public void rentCar(Customer customer, Car car) {
            if (availableCars.contains(car)) {
                activeRentals.put(customer, car);
                rentTimes.put(car, LocalDateTime.now());
                availableCars.remove(car);
                rentalHistory.add("Car " + car.id + " rented by Customer " + customer.name + " at " + LocalDateTime.now());
                System.out.println(customer.name + "  rented car " + car.brand + " " + car.model+"  at "+ LocalDateTime.now());
            } else {
                System.out.println("Car is not available for rent.");
            }

        }

        public void returnCar(Customer customer){
            Car car = activeRentals.get(customer);
            if(car !=null){
                activeRentals.remove(customer);
                rentTimes.remove(car);
                availableCars.add(car);
                rentalHistory.add("Car " + car.id + " returned by Customer " + customer.name + " at " + LocalDateTime.now());
            }
        }
        public void printActiveRentals(){
            System.out.println(" ");
            System.out.println("Active Rentals----");
            for(Map.Entry<Customer,Car> entry : activeRentals.entrySet()){
                Customer customer = entry.getKey();
                Car car = entry.getValue();
                LocalDateTime rentTime = rentTimes.get(car);
                System.out.println(customer.name + customer.id + " has rented  "+car.brand + car.model + " at " + rentTime);
            }
        }
        public void printAvailableCars(){
            System.out.println(" ");
            System.out.println("Aviable Cars----");
            for(Car car : availableCars){
                System.out.println("Car ID: " + car.id + ", Brand: " + car.brand + ", Model: " + car.model + ", Year: " + car.year);
            }

        }
        public void printRentalHistory(){
            System.out.println(" ");
            System.out.println("Rental History------");
            for(String r : rentalHistory){
                System.out.println(r);
            }
        }


}
