public class Scooter implements Transport {
    private final double ratePerKm = 0.7;
    private final double speed = 25.0;

    @Override
    public double calculateFare(double distance) {
        return distance * ratePerKm;
    }

    @Override
    public double calculateFare(double distance, int passengers) {
        return passengers <= 1 ? calculateFare(distance) : 0; // yalnız bir nəfərlik
    }

    @Override
    public double calculateTime(double distance) {
        return distance / speed;
    }

    @Override
    public String getTransportInfo() {
        return "Scooter - Rate: " + ratePerKm + " per km, Speed: " + speed + " km/h";
    }
}
