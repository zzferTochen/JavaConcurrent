package com.chen;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huchen
 * @description
 * @since 2022/2/17 17:20
 */
@Slf4j
public class TwoParseTerminationTest {
    public static void main(String[] args) throws InterruptedException {
        TwoParseTermination  twoParseTermination = new TwoParseTermination();

        twoParseTermination.start();

        Thread.sleep(3500);

        twoParseTermination.stop();
    }

}

@Data
@Slf4j
class TwoParseTermination{
    private Thread monitor;

    public void start(){
        monitor = new Thread(()->{
            while (true){
                Thread cuurent = Thread.currentThread();
                if(cuurent.isInterrupted()){
                    log.info("reading for dead....");
                    break;
                }
                try {
                    Thread.sleep(2000);
                    log.info("i'm looking you!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    monitor.interrupt();
                }
            }
        });
        monitor.start();
    }

    public void stop(){
        monitor.interrupt();
    }
}