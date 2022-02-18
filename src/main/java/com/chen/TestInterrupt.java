package com.chen;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huchen
 * @description
 * @since 2022/2/17 15:42
 */
@Slf4j
public class TestInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.info("sleep....");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");

        t1.start();
        Thread.sleep(1000);
        log.info("interrupt");
        t1.interrupt();
        log.info("mark : {}",t1.isInterrupted());
    }
}
