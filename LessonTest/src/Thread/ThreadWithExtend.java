package Thread;

import java.time.LocalTime;

public class ThreadWithExtend extends Thread {

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Say: " + i);
            System.out.println("Thread işə düşdü: " + Thread.currentThread().getName()+ LocalTime.now());
        }
    }
}
