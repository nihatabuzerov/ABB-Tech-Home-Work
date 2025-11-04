    import java.util.*;

    public class CollectionTest {
        public static void main(String[] args) {


            List<Car> cars = new ArrayList<>();
            Car car = new Car();
            Car car1 = new Car();
            car1.setModel("BMW");
            car1.setOdometer(12000);
            car1.setColor("Black");

            Car car2 = new Car();
            car2.setModel("Audi");
            car2.setOdometer(45000);
            car2.setColor("White");

            Car car3 = new Car();
            car3.setModel("Mercedes");
            car3.setOdometer(8000);
            car3 .setColor("Silver");
            cars.add(car1);
            cars.add(car2);
            cars.add(car3);
            for (int i = 0; i < cars.size(); i++) {
                System.out.println(cars.get(i));
            }


            Stack<String> stack = new Stack<>();

            stack.push("BMW");
            stack.push("Audi");
            stack.push("Mercedes");

            System.out.println("For-each ilə:");
            for (String c : stack) {
                System.out.println(car);
            }

            System.out.println("\nKlassik for ilə:");
            for (int i = 0; i < stack.size(); i++) {
                System.out.println(stack.get(i));
            }



            Set<Car> ca = new HashSet<>();

            Car c1 = new Car("BMW", 5000);
            Car c2 = new Car("Audi", 12000);
            Car c3 = new Car("Mercedes", 8000);
            Car c4 = new Car("BMW", 5000);
            cars.add(c1);
            cars.add(c2);
            cars.add(c3);
            boolean added = cars.add(c4);

            System.out.println("c4 eklendi mi? " + added);
            System.out.println("HashSet boyutu: " + cars.size());


        }
    }
