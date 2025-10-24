public interface Transport {
    double calculateFare(double distance); // məsafəyə görə qiymət
    double calculateFare(double distance, int passengers); // overload
    double calculateTime(double distance); // məsafəyə görə vaxt
    String getTransportInfo(); // ü
}
