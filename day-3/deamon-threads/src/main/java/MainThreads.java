public class MainThreads {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            System.out.println("T1 is started");
            for (int i = 0; i < 100; i++) {
                System.out.println(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("T1 is about to exit");
        });
        t1.start();

        System.out.println("Main thread is about to exit");
    }
}
