package com.chen.chapter4;

public class TestSynchronized {
//    static int counter = 0 ;
//    static final Object room = new Object();
    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.increment();
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (room){
                    room.decrement();
                }
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(room.getCounter());
    }
}

class Room{
    private int counter = 0;

    public void increment(){
        synchronized (this){
            counter++;
        }
    }

    public void decrement(){
        synchronized (this){
            counter--;
        }
    }

    /**
     * 在这里也要加锁的原因就是：counter不是一个静态变量了。为了防止在技术中途来读数据，造成数据错误
     * @return
     */
    public int getCounter(){
        synchronized (this){
            return counter;
        }
    }
}
