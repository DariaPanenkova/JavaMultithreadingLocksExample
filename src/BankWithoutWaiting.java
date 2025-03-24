import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankWithoutWaiting {
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        new User2("Pavel", lock);
        new User2("Nadya", lock);
        new User2("Petr", lock);
        Thread.sleep(5000);
        new User2("Dany", lock);
        new User2("Sveta", lock);
    }
}

class User2 extends Thread {
    String name;
    private Lock lock;

    public User2(String name, Lock lock) {
        this.name = name;
        this.lock = lock;
        this.start();
    }

    @Override
    public void run() {
        if (lock.tryLock()) {
            try {
                System.out.println(name + " is using Bank");
                Thread.sleep(2000);
                System.out.println(name + " finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }else{
            System.out.println(name + " don't want to wait");
        }
    }
}
