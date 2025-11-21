package Thread;

public class NotificationService {
    public void notifyUser() throws InterruptedException {
        synchronized (this){
            System.out.println("try to send notification to user...");
            this.wait();
            System.out.println("User has been notified.");
        }
    }
    public void notifyAdmin() {
        System.out.println("try to send notification to admin...");
        synchronized (this) {
            System.out.println("Admin has been notified.");
            this.notify();
        }
    }
}
