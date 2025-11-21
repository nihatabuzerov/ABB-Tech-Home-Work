package ObservePattern;

public class Practice {
    public static void main(String[] args) {

        NewsPublisher publisher = new NewsPublisher();

        Subscriber s1 = news -> System.out.println("Subscriber-1 xəbər aldı: " + news);
        Subscriber s2 = news -> System.out.println("Subscriber-2 xəbər aldı: " + news);
        Subscriber s3 = news -> System.out.println("Subscriber-3 xəbər aldı: " + news);

        publisher.addSubscriber(s1);
        publisher.addSubscriber(s2);
        publisher.addSubscriber(s3);

        new Thread(() -> {
            while (true) publisher.waitForNews(s1);
        }).start();

        new Thread(() -> {
            while (true) publisher.waitForNews(s2);
        }).start();

        new Thread(() -> {
            while (true) publisher.waitForNews(s3);
        }).start();

        int count = 1;
        while (true) {
            String news = "Xəbər #" + count++;
            publisher.publishNews(news);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
