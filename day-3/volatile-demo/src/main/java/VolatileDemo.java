public class VolatileDemo extends Object{

    private static int value = 0;
    private static boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        // Caching
        Thread t1 = new Thread(()->{
            try {
                while(!flag){
                    value++;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(()->{
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            flag = true;
            System.out.println("Flag changed");
        });

        t1.start();
        t2.start();

        t2.join();
        System.out.println("T2 Joined");
        System.out.println("Value: " + value);
        System.out.println("Flag: " + flag);
        t1.join();
        System.out.println("T1 Joined");


    }

    public static native void myNativeMethod();
}
