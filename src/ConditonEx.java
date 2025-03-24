import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditonEx {
    public static void main(String[] args) throws InterruptedException {
        Market market = new Market();
        Producer producer = new Producer(market);
        Consumer consumer = new Consumer(market);
        Thread thread1 = new Thread(producer);
        Thread thread2 = new Thread(consumer);
        thread2.start();
       // Thread.sleep(3000);
        thread1.start();
    }
}

class Market {
    private int breadCount = 0;
    private Lock locker = new ReentrantLock();
    private Condition condition = locker.newCondition();

    public void getBread() {
        locker.lock();
        try {
            while (breadCount < 1) { //хлеб закончился
                condition.await();
            }

            breadCount--;

            System.out.println("Потребитель купил 1 хлеб");
            System.out.println("В магазине кол-во хлеба = " + breadCount);
            condition.signalAll();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            locker.unlock();
        }
    }

    public void putBread() {
        locker.lock();
        try {
            while (breadCount >= 5) { //максимум на витрине 5 штук
                condition.await();
            }

            breadCount++;
            System.out.println("Производитель добавил 1 хлеб");
            System.out.println("В магазине кол-во хлеба = " + breadCount);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
    }
}

class Producer implements Runnable {
    Market market;

    Producer(Market market) {
        this.market = market;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) { //в день всего 10 штук может сделать
            market.putBread();
        }
    }
}

class Consumer implements Runnable {
    Market market;

    Consumer(Market market) {
        this.market = market;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) { //в день всего 10 штук может купить
            market.getBread();
        }
    }
}