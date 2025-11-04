import java.util.LinkedList;
import java.util.Queue;

public class Stop {
    private String name;
    private Queue<Passenger> queue = new LinkedList<>();

    public Stop(String name) {
        this.name = name;
    }

    public void addPassenger(Passenger p) {
        if (queue.size() < 10) {
            queue.add(p);
        }
    }

    public Queue<Passenger> getQueue() {
        return queue;
    }

    public String getName() {
        return name;
    }
}
