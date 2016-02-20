package sample;

/**
 * Created by libin on 2/20/16.
 */
public class FutureSampleApplication {
    public static void main(String arg[]){
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        };

        task.run();

        Thread thread = new Thread(task);
        thread.start();

        System.out.println("Done!");
    }
}
