public class Bus implements Transport {
    private final double ratePerKm = 0.5;
    private final double speed = 50.0;

    @Override
    public double calculateFare(double distance) {
        return distance * ratePerKm;
    }

    @Override
    public double calculateFare(double distance, int passengers) {
        return calculateFare(distance) * passengers * 0.8; // endirimli çox sərnişin
    }

    @Override
    public double calculateTime(double distance) {
        return distance / speed;
    }

    @Override
    public String getTransportInfo() {
        return "Bus - Rate: " + ratePerKm + " per km, Speed: " + speed + " km/h";
    }
}
