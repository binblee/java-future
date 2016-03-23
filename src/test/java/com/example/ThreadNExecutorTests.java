package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

        task.run();

        Thread taskThread = new Thread(task);
        taskThread.start();
    }
}
