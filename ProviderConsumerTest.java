import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

class Provider implements Runnable {
    public void run() {
        Random random = new Random();
        try {
            for (int i = 0; i < 5; i++) {
                int j = random.nextInt();
                ProviderConsumerTest.queue.put(j);
                System.out.println(Thread.currentThread().getName() + " product: " + j);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    public void run() {
        Integer data;
        for (int i = 0; i < 5; i++) {
            try {
            data = ProviderConsumerTest.queue.take();
            System.out.println(Thread.currentThread().getName() + " comsume: " + data);
            Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ProviderConsumerTest {
    public static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        Provider p1 = new Provider();
        Provider p2 = new Provider();
        Consumer c1 = new Consumer();
        Consumer c2 = new Consumer();

        Thread t1 = new Thread(p1);
        Thread t2 = new Thread(p2);
        Thread t3 = new Thread(c1);
        Thread t4 = new Thread(c2);

        t1.setName("Provider1");
        t2.setName("Provider2");
        t3.setName("Consumer1");
        t4.setName("Consumer2");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}