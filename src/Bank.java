import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        new User1("Pavel", lock);
        new User1("Petr", lock);
        new User1("Dany", lock);
        new User1("Sveta", lock);
        new User1("Nadya", lock);
    }
}

class User1 extends Thread{
    String name;
    private Lock lock;
    public User1(String name, Lock lock){
        this.name = name;
        this.lock = lock;
        this.start();
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " is waiting");
            lock.lock();
            System.out.println(name + " is using Bank");
            Thread.sleep(2000);
            System.out.println(name + " finished");
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}