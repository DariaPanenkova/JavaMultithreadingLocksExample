import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Звонок в другом приложении не может быть выполнен, пока не завершится текущий активный звонок
public class RunnableCallLockEx {
    private final Lock lock = new ReentrantLock();

    void mobileCall() {
        lock.lock();
        try {
            System.out.println("Mobile call starts");
            Thread.sleep(1000);
            System.out.println("Mobile call ends");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void skypeCall(){
        lock.lock();
        try {
            System.out.println("Skype call starts");
            Thread.sleep(1500);
            System.out.println("Skype call ends");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void whatsAppCall(){
        lock.lock();
        try {
            System.out.println("WhatsApp call starts");
            Thread.sleep(2000);
            System.out.println("WhatsApp call ends");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        RunnableCallLockEx call = new RunnableCallLockEx();
        Thread thread1 = new Thread(new Thread(new Runnable() {
                @Override
                public void run() {
                    call.mobileCall();
                }
            }));
        Thread thread2 = new Thread(new Thread(new Runnable() {
                @Override
                public void run() {
                    call.skypeCall();
                }
            }));
        Thread thread3 = new Thread(new Thread(new Runnable() {
                @Override
                public void run() {
                    call.whatsAppCall();
                }
            }));

        thread1.start();
        thread2.start();
        thread3.start();
    }
}