package com.chen.chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

@Slf4j
public class ExericseSell {
    public static void main(String[] args) throws InterruptedException {

        //创建一个售票窗口，只有一个售票窗口，有1000张票
        TicketWindow ticketWindow = new TicketWindow(1000);

        //卖出的票数记录  放在线程安全的集合中
        List<Integer> amountList = new Vector<>();

        //所有线程的集合
        List<Thread> threadList = new ArrayList<>();

        //模拟买票
        for (int i = 0; i < 4000; i++) {
            Thread thread = new Thread(() -> {
                int sell = ticketWindow.sell(randomAmount());

                //睡眠一个随机时间，让上下文切换的几率变大，让问题更容易暴露出来
                try {
                    Thread.sleep(randomAmount());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                amountList.add(sell);
            });
            threadList.add(thread);
            thread.start();
        }

        //等待所有的线程都执行完成
        for (Thread thread : threadList) {
            thread.join();
        }

        //统计卖出的票数和剩余的票数
        log.info("余票数：{}",ticketWindow.getCount());

        //MapToInt 把包装类转换成int类型，方便调用sum方法求和
        log.info("卖出的票数：{}",amountList.stream().mapToInt(i->i).sum());
    }

    static Random random = new Random();

    //返回一个1-5的随机数
    public static int randomAmount(){
        return random.nextInt(5)+1;
    }
}

//售票窗口
class TicketWindow{
    private int count;

    public TicketWindow(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public int sell(int amount){
        if(amount<=count){
            count-=amount;
            return amount;
        }else{
            return 0;
        }
     }
}
