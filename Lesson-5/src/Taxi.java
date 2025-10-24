public class Taxi implements Transport {
    private final double ratePerKm = 1.5;
    private final double speed = 70.0;

    @Override
    public double calculateFare(double distance) {
        return distance * ratePerKm;
    }

    @Override
    public double calculateFare(double distance, int passengers) {
        return calculateFare(distance) + (passengers - 1) * 2.0; // əlavə sərnişin üçün əlavə haqq
    }

    @Override
    public double calculateTime(double distance) {
        return distance / speed;
    }

    @Override
    public String getTransportInfo() {
        return "Taxi - Rate: " + ratePerKm + " per km, Speed: " + speed + " km/h";
    }
}
