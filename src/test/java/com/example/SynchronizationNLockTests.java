package com.example;

import com.example.concurrent.ConcurrentUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * Created by libin on 3/25/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JavaFutureApplication.class)
public class SynchronizationNLockTests {

    @Test
    public void contextLoad(){

    }

    private int count = 0;

    public void increase(){
        count += 1;
    }


    @Test
    public void testWithoutSynchronization(){
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        IntStream.range(0,1000).forEach((i) -> executorService.submit(this::increase));
        ConcurrentUtils.stop(executorService);
        System.out.println(count);
    }

    synchronized public void synchronizedIncrease(){
        count += 1;
    }

    @Test
    public void testWithSynchronization(){
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        IntStream.range(0,1000).forEach(i -> executorService.submit(this::synchronizedIncrease));
        ConcurrentUtils.stop(executorService);
        Assert.assertEquals(1000, count);
    }

    ReentrantLock lock = new ReentrantLock();
    public void lockedIncrease(){
        lock.lock();
        try{
            count ++;
        }finally {
            lock.unlock();
        }
    }

    @Test
    public void testReentrantLock(){
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        IntStream.range(0, 1000).forEach(i -> executorService.submit(this::lockedIncrease));
        ConcurrentUtils.stop(executorService);
        Assert.assertEquals(1000,count);
    }
}
