import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        CarRentalSystem system = new CarRentalSystem();

        Car c1 = new Car(1, "Toyota", "Camry", 2022);
        Car c2 = new Car(2, "BMW", "X5", 2023);
        Car c3 = new Car(3, "Hyundai", "Elantra", 2021);

        system.addCar(c1);
        system.addCar(c2);
        system.addCar(c3);

        Customer u1 = new Customer(101, "Aydin", "AZ12345");
        Customer u2 = new Customer(102, "Nigar", "AZ67890");

        LocalDateTime rentTime1 = LocalDateTime.of(2025, 11, 5, 16, 14, 54, 820279000);
        LocalDateTime rentTime2 = LocalDateTime.of(2025, 11, 5, 16, 14, 54, 821551000);
        LocalDateTime returnTime = LocalDateTime.of(2025, 11, 7, 21, 14, 54, 820279000);

        system.rentCar(u1, c1, rentTime1);
        system.rentCar(u2, c2, rentTime2);

        system.printActiveRentals();
        system.printAvailableCars();

        system.returnCar(u1, returnTime);
        system.printRentalHistory();
        system.printAvailableCars();
    }
}
