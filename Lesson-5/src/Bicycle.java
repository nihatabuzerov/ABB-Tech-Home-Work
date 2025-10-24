public class Bicycle implements Transport {
    private final double ratePerKm = 0.2;
    private final double speed = 15.0;

    @Override
    public double calculateFare(double distance) {
        return distance * ratePerKm;
    }

    @Override
    public double calculateFare(double distance, int passengers) {
        return passengers == 1 ? calculateFare(distance) : 0; // tək sərnişin
    }

    @Override
    public double calculateTime(double distance) {
        return distance / speed;
    }

    @Override
    public String getTransportInfo() {
        return "Bicycle - Rate: " + ratePerKm + " per km, Speed: " + speed + " km/h";
    }
}
