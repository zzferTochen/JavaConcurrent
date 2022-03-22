package com.chen.chapter4;

public class GuardedSuspendtion {
    public static void main(String[] args) {

    }
}

class GuardedObject{
    private Object response;

    //timeout 就是最长等待时间
    private Object get(long timeout) throws InterruptedException {
        synchronized (this){
            long begin = System.currentTimeMillis();

            long passTime = 0;

            while (response == null){
                if(passTime >= timeout){
                    break;
                }
                this.wait(timeout- passTime);
                passTime = System.currentTimeMillis() - begin;
            }
        }
        return response;
    }

    private void complete(Object response){
        synchronized (this){
            this.response =response;
            this.notifyAll();
        }
    }
}
