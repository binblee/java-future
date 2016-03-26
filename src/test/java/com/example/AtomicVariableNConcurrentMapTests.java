package com.example;

import com.example.concurrent.ConcurrentUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Created by libin on 3/26/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JavaFutureApplication.class)
public class AtomicVariableNConcurrentMapTests {
    @Test
    public void loadContext(){

    }

    @Test
    public void testAtomicInteger(){
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        IntStream.range(0,1000).forEach(i -> executorService.submit(
                atomicInteger::incrementAndGet
        ));
        ConcurrentUtils.stop(executorService);
        Assert.assertEquals(1000, atomicInteger.get());
    }

    @Test
    public void testAtomicIntegerUpdateAndGet(){
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Runnable task = () -> atomicInteger.updateAndGet((n) -> n+3);
        IntStream.range(0,1000).forEach(i -> {
            executorService.submit(task);
        }
        );
        ConcurrentUtils.stop(executorService);
        Assert.assertEquals(3000, atomicInteger.get());
    }
}
