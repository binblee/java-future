package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
}
