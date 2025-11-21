package Thread;

public class Practice {
    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            System.out.println("T1 running...");
            counter.increment();
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            System.out.println("T2 running...");
            counter.increment();
        }, "Thread-2");

        Thread t3 = new Thread(() -> {
            System.out.println("T3 running...");
            counter.increment();
        }, "Thread-3");

        Thread t4 = new Thread(() -> {
            System.out.println("T4 running...");
            counter.increment();
        }, "Thread-4");

        t1.start();
        t1.join();

        t2.start();
        t2.join();

        t3.start();
        t3.join();

        t4.start();
        t4.join();
    }
}
