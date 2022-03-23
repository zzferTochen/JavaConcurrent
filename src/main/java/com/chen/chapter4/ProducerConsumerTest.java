package com.chen.chapter4;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

public class ProducerConsumerTest {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(2);


        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(()->{
                    messageQueue.put(new Message(id,"值"+id));
            },"生产者"+ i).start();
        }
        new Thread(()->{
            while(true){
                try {
                    Thread.sleep(1000);
                    Message take = messageQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        },"消费者").start();
    }
}
@Slf4j
//消息队列的类  java线程之间通信
class MessageQueue{

    private LinkedList<Message> list = new LinkedList<>();

    private int capcity;

    public MessageQueue(int capcity) {
        this.capcity = capcity;
    }

    //获取消息
    public Message take(){
        synchronized (list){
            while (list.isEmpty()){
                try {
                    log.info("队列为空，消费者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //从头部取
            Message messageQueue = list.removeFirst();
            log.info("已消费数据：{}",messageQueue.toString());
            list.notifyAll();
            return messageQueue;
        }
    }

    //存入消息
    public void put(Message messageQueue){
        synchronized (list){
            while (list.size() == capcity){
                try {
                    log.info("队列满了，生产者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //放在尾部
            list.addLast(messageQueue);
            log.info("已生产数据：{}",messageQueue.toString());
            list.notifyAll();

        }
    }
}

final class Message {
    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}

