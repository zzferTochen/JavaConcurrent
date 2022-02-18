package com.chen;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author huchen
 * @description
 * @since 2022/2/17 17:50
 */
@Slf4j
public class TestPark {
    public static void main(String[] args) throws InterruptedException {
        test();
    }

    private static void  test() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.info("park...");
            LockSupport.park();
            log.info("unpark...");
            log.info("interrupt state: {}", Thread.interrupted());

            LockSupport.park();
            log.info("unpark...");
        },"t1");
        t1.start();

        Thread.sleep(1000);
        t1.interrupt();
    }
}
