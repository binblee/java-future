package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by libin on 3/23/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JavaFutureApplication.class)
public class ThreadNExecutorTests {

    @Test
    public void contextLoad(){

    }

    @Test
    public void testRunnable(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName);
            }
        };

        Runnable task2 = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName);
        };

        task.run();
        task2.run();

        Thread taskThread = new Thread(task);
        taskThread.start();

        new Thread(()->{
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName);
        }).start();

    }

    @Test
    public void testSingleThreadExecutor(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(()->{
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName);
        });
    }

    @Test
    public void testCallableFuture(){
        Callable<String> task = () -> {
            TimeUnit.SECONDS.sleep(1);
            return "result from callable";
        };

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(task);

        String result = "";
        try {
            result = future.get();
        }catch (ExecutionException e){
            System.out.println(e.getMessage());
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        System.out.println(result);
    }

    @Test(expected = TimeoutException.class)
    public void testFutureTimeout() throws Exception{
        Callable<String> task = () -> {
            TimeUnit.SECONDS.sleep(1);
            return "should not see this result.";
        };

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(task);

        String result = future.get(500,TimeUnit.MILLISECONDS);
    }

    @Test
    public void testInvokeAll() throws Exception {
        List<Callable<String >> tasks = Arrays.asList(
                () -> "task 1",
                () -> "task 2",
                () -> "task 3"
        );

        ExecutorService executorService = Executors.newWorkStealingPool();
        executorService.invokeAll(tasks).stream().map(
                future -> {
                    try {
                        return future.get();
                    }catch(Exception e){
                        throw new IllegalStateException(e);
                    }
                }
        ).forEach(System.out::println);
    }

    @Test
    public void testInvokeAny() throws Exception{
        List<Callable<String>> tasks = Arrays.asList(
                () -> "invoke any: task 1",
                () -> "invoke any: task 2",
                () -> "invoke any: task 3"
        );

        ExecutorService executorService = Executors.newWorkStealingPool();
        String result = executorService.invokeAny(tasks);
        System.out.println(result);
    }

    @Test
    public void testScheduledExecutor() throws Exception{
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Runnable task = () -> System.out.printf("scheduled at %d\n", System.nanoTime());

        ScheduledFuture<?> scheduledFuture = executorService.schedule(task, 3, TimeUnit.SECONDS);
        TimeUnit.MICROSECONDS.sleep(1337);
        long remaining = scheduledFuture.getDelay(TimeUnit.MILLISECONDS);
        System.out.printf("Remaining: %sms\n", remaining);

        scheduledFuture.get();
    }
}
