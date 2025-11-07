//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CarRentalSystem system = new CarRentalSystem();
        //Car yaratmaq
        Car c1 = new Car(1, "BMW", "X5", 2023);
        Car c2 = new Car(2, "Toyota", "Camry", 2021);
        Car c3 = new Car(3, "Honda", "Civic", 2020);
        Car c4 = new Car(4, "Audi", "A6", 2022);
        Car c5 = new Car(5, "Mercedes", "E-Class", 2023);
        //Customer yaratmaq
        Customer cust1 = new Customer(1, "Nihat", "A12345");
        Customer cust2 = new Customer(2, "Vusal", "B67890");


        system.addCar(c1);
        system.addCar(c2);
        system.addCar(c3);
        system.addCar(c4);
        system.addCar(c5);


        system.rentCar(cust1, c1);
        system.rentCar(cust2, c2);


        system.printActiveRentals();
        system.printAvailableCars();
        system.returnCar(cust2);
        system.printRentalHistory();




    }
}