package com.chen;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ChenThreadPooTest {

    private static final int CORE_POOL_SIZE = 5; //核心线程数
    private static final int MAX_POOL_SIZE = 10; //最大线程数
    private static final int QUEUE_CAPACITY = 5; //队列容量
    private static final Long KEEP_ALIVE_TIME = 1L;//当线程数大雨核心线程数时，多余线程存活的最长时间

    public static void main(String[] args) {
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

        log.info("string collection size:{}",partition.size());
        for (List<String> strings : partition) {
            executor.execute(()->{
                log.info("string size:{}",strings.size());
                for (String string : strings) {
                    log.info("string value;{}",string);
                }
            });
        }

        executor.shutdown();

        while(!executor.isTerminated()){
            //System.out.println("线程池还没有完全关闭");
        }
        log.info("over!");

    }
}
