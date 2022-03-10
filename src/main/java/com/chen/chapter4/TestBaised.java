package com.chen.chapter4;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

@Slf4j
public class TestBaised {
    public static void main(String[] args) throws InterruptedException {
        //Thread.sleep(5000);
        Test test = new Test();
        ClassLayout classLayout = ClassLayout.parseInstance(test);
        new Thread(()->{
            log.info(classLayout.toPrintable());
            synchronized (test){
                log.info(classLayout.toPrintable());
            }
            log.info(classLayout.toPrintable());
        },"t1").start();

    }
}
