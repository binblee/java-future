package com.example;

import com.example.concurrent.ConcurrentUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
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

    @Test
    public void testReadWriteLock(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        ReadWriteLock lock = new ReentrantReadWriteLock();
        executorService.submit(()->{
            lock.writeLock().lock();
            try{
                ConcurrentUtils.sleep(1);
                map.put("foo","bar");
            }finally {
                lock.writeLock().unlock();
            }
        });

        Runnable readTask = ()->{
            lock.readLock().lock();
            try{
                System.out.println(map.get("foo"));
                ConcurrentUtils.sleep(1);
            }finally {
                lock.readLock().unlock();
            }
        };

        executorService.submit(readTask);
        executorService.submit(readTask);

        ConcurrentUtils.stop(executorService);
    }

    @Test
    public void testStampedLock(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Map<String,String> map = new HashMap<>();
        StampedLock lock = new StampedLock();
        executorService.submit(()->{
            long stamp = lock.writeLock();
            try{
                ConcurrentUtils.sleep(1);
                map.put("foo","bar");
            }finally {
                lock.unlockWrite(stamp);
            }
        });

        Runnable readTask = () -> {
            long stamp = lock.writeLock();
            try{
                System.out.println(map.get("foo"));
                ConcurrentUtils.sleep(1);
            }finally {
                lock.unlockWrite(stamp);
            }
        };

        executorService.submit(readTask);
        executorService.submit(readTask);
        ConcurrentUtils.stop(executorService);
    }
}
