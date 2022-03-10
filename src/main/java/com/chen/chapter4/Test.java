package com.chen.chapter4;

public class Test {
    static final Object lock  = new Object();
    static int count = 0;

    public static void main(String[] args) {
        synchronized (lock){
            count++;
        }
    }
}
