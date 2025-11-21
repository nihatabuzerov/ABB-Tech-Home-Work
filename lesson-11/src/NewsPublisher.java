import java.util.ArrayList;
import java.util.List;

public class NewsPublisher {
    private String news;
    private final List<Subscriber> subscribers = new ArrayList<>();

    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public synchronized void publishNews(String news) {
        this.news = news;
        System.out.println("Publisher: Yeni xəbər yayımlandı -> " + news);
        notifyAll(); // Subscriber-ləri oyadır
    }

    public synchronized void waitForNews(Subscriber subscriber) {
        while (news == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        subscriber.update(news);
        news = null; // Sonrakı xəbər üçün reset
    }
}