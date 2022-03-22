package com.chen.chapter4;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestWait {
    static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            log.info("执行。。。");
            synchronized (lock){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("执行其他代码。。。");
        },"t1").start();

        new Thread(()->{
            log.info("执行。。。");
            synchronized (lock){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("执行其他代码。。。");
        },"t2").start();

        Thread.sleep(2000);
        synchronized (lock){
            lock.notify();
        }

    }
}
