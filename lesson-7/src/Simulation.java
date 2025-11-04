import java.util.Arrays;
import java.util.List;

public class Simulation {
    public static void main(String[] args) {

        Stop stop1 = new Stop("Stop 1");
        Stop stop2 = new Stop("Stop 2");
        Stop stop3 = new Stop("Stop 3");

        stop1.addPassenger(new Passenger("Aysel", true));
        stop1.addPassenger(new Passenger("Rauf", false));
        stop1.addPassenger(new Passenger("Ali", false));

        stop2.addPassenger(new Passenger("Nigar", true));
        stop2.addPassenger(new Passenger("Samir", true));
        stop2.addPassenger(new Passenger("Murad", false));

        stop3.addPassenger(new Passenger("Sevinc", true));
        stop3.addPassenger(new Passenger("Leyla", false));
        stop3.addPassenger(new Passenger("Tural", false));

        List<Stop> stops = Arrays.asList(stop1, stop2, stop3);

        Bus bus = new Bus();

        for (Stop stop : stops) {
            System.out.println("\n🚏 " + stop.getName() + " reached.");

            bus.dropPassengers();
            bus.boardPassengers(stop);

            bus.printState();
            System.out.println(stop.getName() + " remaining: " + stop.getQueue());
        }

        System.out.println("\n✅ Simulation finished.");
        System.out.println("Final passengers in bus: " + bus.getPassengers());
    }
}
