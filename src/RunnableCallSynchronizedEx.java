//Звонок в другом приложении не может быть выполнен, пока не завершится текущий активный звонок

public class RunnableCallSynchronizedEx {
    static final Object lock = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(new RunnableMobileSynchronizedEx());
        Thread thread2 = new Thread(new RunnableSkypeSynchronizedEx());
        Thread thread3 = new Thread(new RunnableWhatsAppSynchronizedEx());

        thread1.start();
        thread2.start();
        thread3.start();
    }

    void mobileCall() {
        synchronized (lock){
            System.out.println("Mobile call starts");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Mobile call ends");
        }
    }

    synchronized void skypeCall(){
        synchronized(lock) {
            System.out.println("Skype call starts");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Skype call ends");
        }
    }

    synchronized void whatsAppCall(){
        synchronized(lock) {
            System.out.println("WhatsApp call starts");
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("WhatsApp call ends");
        }
    }
}

class RunnableMobileSynchronizedEx implements Runnable{
    @Override
    public void run() {
        new RunnableCallSynchronizedEx().mobileCall();
    }
}

class RunnableSkypeSynchronizedEx implements Runnable{
    @Override
    public void run() {
        new RunnableCallSynchronizedEx().skypeCall();
    }
}

class RunnableWhatsAppSynchronizedEx implements Runnable{
    @Override
    public void run() {
        new RunnableCallSynchronizedEx().whatsAppCall();
    }
}
