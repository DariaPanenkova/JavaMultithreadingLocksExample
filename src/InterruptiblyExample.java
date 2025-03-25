import java.util.concurrent.locks.ReentrantLock;

public class InterruptiblyExample {
    public static void main(final String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        //тут сработает прервывание
        Thread thread = new Thread(() -> {
            int i = 0;
            System.out.println("before entering ReentrantLock block");
            try {
                lock.lockInterruptibly();
                try {
                    while (i < 100) {
                        System.out.println("in the ReentrantLock block counting: " + i++);
                    }
                } finally {
                    lock.unlock(); // Освобождаем блокировку после выполнения
                }
            } catch (InterruptedException e) {
                System.out.println("ReentrantLock block interrupted");
            }
        });

        //версия при которой зависаем
//        Thread thread = new Thread(() -> {
//            int i = 0;
//            System.out.println("before entering ReentrankLock block");
//            try {
//                lock.lock();
//                while (i < 100) {
//                    System.out.println("in the ReentrankLock block counting: " + i++);
//                }
//            }finally {
//                lock.unlock(); // Освобождаем блокировку после выполнения
//            }
//        });

        lock.lock(); // Захватываем блокировку в главном потоке
        thread.start();
        Thread.sleep(10); // Даем потоку немного времени для запуска
        thread.interrupt();
    }
}