package com.chen;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class Demo02 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.info("running");
                Thread.sleep(1000);
                return 100;
            }
        });

        Thread t = new Thread(task,"t1");
        t.start();
        log.info("{}",task.get());
    }
}
