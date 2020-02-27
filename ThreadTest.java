import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private static int count = 0;

    public static void main(String[] args) {
        Thread A = new Thread(() -> {
            lock.lock();
            try {
                while (true) {
                    if (count == 9) {
                        break;
                    }
                    if (count % 3 == 0) {
                        System.out.println("A");
                        count++;
                        condition.signalAll();
                    } else {
                        try {
                            condition.await();
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
            } finally {
                lock.unlock();
            }
        });

        Thread B = new Thread(() -> {
            lock.lock();
            try {
                while (true) {
                    if (count == 9) {
                        break;
                    }
                    if (count % 3 == 1) {
                        count++;
                        System.out.println("B");
                        condition.signalAll();
                    } else {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } finally {
                lock.unlock();
            }
        });

        Thread C = new Thread(() -> {
            lock.lock();
            try {
                while (true) {
                    if (count == 9) {
                        break;
                    }
                    if (count % 3 == 2) {
                        count++;
                        System.out.println("C");
                        condition.signalAll();
                    } else {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } finally {
                lock.unlock();
            }
        });

        A.start();
        B.start();
        C.start();
    }
}