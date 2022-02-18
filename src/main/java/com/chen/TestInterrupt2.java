package com.chen;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huchen
 * @description
 * @since 2022/2/17 15:42
 */
@Slf4j
public class TestInterrupt2 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true){
                boolean interrupted = Thread.currentThread().isInterrupted();
                if(interrupted){
                    log.info("interrupt by main");
                    break;
                }
            }
        }, "t1");

        t1.start();
        Thread.sleep(1000);
        log.info("interrupt");
        t1.interrupt();
        log.info("mark : {}",t1.isInterrupted());
    }
}
