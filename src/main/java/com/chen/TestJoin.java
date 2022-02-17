package com.chen;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


/**
 * @author huchen
 * @description
 * @since 2022/2/17 14:40
 */
@Slf4j
public class TestJoin {
    static int r =0;
    public static void main(String[] args) throws InterruptedException {
        test();
    }

    private static void test() throws InterruptedException {
        log.info(" main start");
        Thread t1 = new Thread(() -> {
            log.info("son start ");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("son end");
            r = 10;
        },"t1");

        t1.start();
        t1.join();
        log.info("result is {}",r);
        log.info("main end");
    }
}
