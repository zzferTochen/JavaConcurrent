package com.chen;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo01 {
    public static void main(String[] args) {
        Thread thread = new Thread("t1") {
            @Override
            public void run(){
                log.info("hello t1");
            }
        };
        thread.start();
        log.info("hello main");

        Runnable task = new Runnable() {

            @Override
            public void run() {
                log.info("hello t2");
            }
        };

    Runnable r = ()->{
        log.info("hello");
    };
    Thread t2 = new Thread(r, "t2");
    t2.start();
    log.info("hello main 2");


    }
}
