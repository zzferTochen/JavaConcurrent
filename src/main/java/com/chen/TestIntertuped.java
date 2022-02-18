package com.chen;


import lombok.extern.slf4j.Slf4j;

/**
 * @author huchen
 * @description
 * @since 2022/2/17 13:53
 */
@Slf4j
public class TestIntertuped {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.info("enter sleep ....");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    log.info("wake up ....");
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("interrupt....");
        t1.interrupt();

    }
}
