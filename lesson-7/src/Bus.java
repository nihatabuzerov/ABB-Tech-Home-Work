import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bus {
    private List<Passenger> passengers = new ArrayList<>();
    private final int CAPACITY = 5;
    private Random random = new Random();

    public void dropPassengers() {
        int count = random.nextInt(3); // 0 - 2 nəfər düşə bilər
        for (int i = 0; i < count && !passengers.isEmpty(); i++) {
            Passenger removed = passengers.remove(0);
            System.out.println(removed + " left the bus.");
        }
    }

    public void boardPassengers(Stop stop) {

        var priorityList = new ArrayList<Passenger>();
        for (Passenger p : stop.getQueue()) {
            if (p.isPriority()) priorityList.add(p);
        }

        for (Passenger p : priorityList) {
            if (passengers.size() < CAPACITY) {
                passengers.add(p);
                stop.getQueue().remove(p);
                System.out.println(p + " boarded the bus.");
            }
        }

        var normalList = new ArrayList<Passenger>();
        for (Passenger p : stop.getQueue()) {
            if (!p.isPriority()) normalList.add(p);
        }

        for (Passenger p : normalList) {
            if (passengers.size() < CAPACITY) {
                passengers.add(p);
                stop.getQueue().remove(p);
                System.out.println(p + " boarded the bus.");
            }
        }
    }


    private void boardWithCheck(Stop stop, Passenger p) {
        if (passengers.size() < CAPACITY && stop.getQueue().contains(p)) {
            passengers.add(p);
            stop.getQueue().remove(p);
            System.out.println(p + " boarded the bus.");
        }
    }

    public void printState() {
        System.out.println("Bus now: " + passengers);
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }
}
