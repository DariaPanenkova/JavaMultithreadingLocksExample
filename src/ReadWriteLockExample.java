import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.ThreadLocalRandom;

public class ReadWriteLockExample {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private static String sharedResource = "Initial Value";

    public static void main(String[] args) {
        Thread thread1 = new Thread(ReadWriteLockExample::readResource);
        Thread thread2 = new Thread(ReadWriteLockExample::readResource);
        Thread thread3 = new Thread(ReadWriteLockExample::readResource);
        Thread thread4 = new Thread(ReadWriteLockExample::writeResource);
        Thread thread5 = new Thread(ReadWriteLockExample::writeResource);
        Thread thread6 = new Thread(ReadWriteLockExample::writeResource);

        thread1.start();
        thread4.start();
        thread3.start();
        thread5.start();
        thread2.start();
        thread6.start();
    }

    private static void readResource() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " читает: " + sharedResource);
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }

    private static void writeResource() {
        lock.writeLock().lock();
        try {
            String newValue = sharedResource + " | " + Thread.currentThread().getName();
            System.out.println(Thread.currentThread().getName() + " записывает: " + newValue);
            sharedResource = newValue;
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
}