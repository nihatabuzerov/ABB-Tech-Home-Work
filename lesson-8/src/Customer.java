public class Customer {
    String name;
    int id;
    String lisaenceNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;
        return id == customer.id;
    }
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
    @Override
    public String toString() {
        return "Customer{" +
                "Name='" + name + '\'' +
                ", id=" + id +
                ", lisaenceNumber=" + lisaenceNumber +
                '}';
    }
    public Customer(int id, String name, String lisaenceNumber) {
        this.name = name;
        this.id = id;
        this.lisaenceNumber = lisaenceNumber;
    }
}
