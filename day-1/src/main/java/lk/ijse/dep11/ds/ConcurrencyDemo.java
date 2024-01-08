package lk.ijse.dep11.ds;

public class ConcurrencyDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                doSomething();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t1");
        t1.start();

        doSomething();
    }

    static Object response = null;

    public static void doSomething() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " Entered");
        synchronized (ConcurrencyDemo.class) {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " - " + i);
                Thread.sleep((long) ((Math.random() * 10)));
                if (i == 5) {
                    getResponse();
                    while (response == null){       // this is how we handle supirous wake up
                        ConcurrencyDemo.class.wait();
                    }
                    System.out.println("Response: " + response);
                    response = null;
                }
            }
        }
    }

    public static void getResponse(){
        new Thread(()->{
            synchronized (ConcurrencyDemo.class) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                response = new Object();
                ConcurrencyDemo.class.notify();
            }
        }, "response-thread").start();
    }
}
