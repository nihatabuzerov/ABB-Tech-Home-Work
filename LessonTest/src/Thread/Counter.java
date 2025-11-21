package Thread;

public  class Counter {
    private int count;
    public synchronized void increment() {
        count++;
        System.out.println("Count: " + count);
    }
}
    