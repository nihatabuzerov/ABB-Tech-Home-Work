public class TransportManager {

    public static Transport getTransport(TransportType type) {
        switch (type) {
            case BUS:
                return new Bus();
            case TAXI:
                return new Taxi();
            case BICYCLE:
                return new Bicycle();
            case SCOOTER:
                return new Scooter();
            default:
                throw new IllegalArgumentException("Invalid transport type!");
        }
    }

    public static void main(String[] args) {
        TransportType userChoice = TransportType.TAXI;
        double distance = 10.0;
        int passengers = 2;

        Transport transport = getTransport(userChoice);

        System.out.println("Transport Info: " + transport.getTransportInfo());
        System.out.println("Fare for " + passengers + " passenger(s): " +
                transport.calculateFare(distance, passengers));
        System.out.println("Estimated time: " +
                transport.calculateTime(distance) + " hours");
    }
}