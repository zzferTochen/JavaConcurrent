package com.chen;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huchen
 * @description completableFuture test
 * @since 2022/3/22 10:19
 */
@Slf4j
public class ChenThreadPooTest2 {

    private static final int CORE_POOL_SIZE = 5; //核心线程数
    private static final int MAX_POOL_SIZE = 10; //最大线程数
    private static final int QUEUE_CAPACITY = 5; //队列容量
    private static final Long KEEP_ALIVE_TIME = 1L;//当线程数大雨核心线程数时，多余线程存活的最长时间

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建一些数组，将数据查出并且分组
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("test" + i);
        }

        //平均划分为5份
        List<List<String>> partition = Lists.partition(list, 20);


        //线程池的方法将
        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<>(QUEUE_CAPACITY), new ThreadFactory() {

            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "test-thread-pool-" + threadNumber.getAndIncrement());
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy());

        CompletableFuture<Void> task1 = CompletableFuture.runAsync(new Task(5), executor);
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(new Task(8), executor);
        CompletableFuture<Void> task3 = CompletableFuture.runAsync(new Task(2), executor);
        CompletableFuture<Void> task4 = CompletableFuture.runAsync(new Task(4), executor);

        CompletableFuture.allOf(task1, task2, task3, task4).get();

    }
}

@Slf4j
class Task implements Runnable{


    private Integer count;

    public Task(Integer count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (Integer i = count; i>=0; i--) {
            log.info("I'm counting {}", i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

