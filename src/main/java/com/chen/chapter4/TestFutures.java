//package com.chen.chapter4;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.Hashtable;
//import java.util.Map;
//import java.util.Set;
//import java.util.logging.Logger;
///**
// * @author huchen
// * @description
// * @since 2022/3/15 10:16
// */
//@Slf4j
//public class TestFutures {
//    public static void main(String[] args) throws InterruptedException {
//        for (int i = 0; i < 3; i++) {
//            new People().start();
//        }
//        Thread.sleep(1000);
//        for (Integer id : Mailbox.getIds()) {
//            new Postman(id,"内容"+ id).start();
//        }
//    }
//}
//
//@Slf4j
//class People extends Thread{
//    @Override
//    public void run() {
//        //收信
//        GuardedObject guardedObject = Mailbox.createGuardedObject();
//        log.info("开始收信{}",guardedObject.getId());
//        Object mail = guardedObject.get(5000);
//        log.info("收到信{}",mail);
//    }
//}
//
//@Slf4j
//class Postman extends Thread{
//    private int id;
//
//    private String mail;
//
//    public Postman(int id,String mail){
//        this.id = id;
//        this.mail =mail;
//    }
//
//    @Override
//    public void run() {
//        GuardedObject guardedObject = Mailbox.getGuardedObject(id);
//        log.info("送信 id{}  内容{}",id,mail);
//        guardedObject.complete(mail);
//    }
//}
//
//class Mailbox{
//    private static Map<Integer,GuardedObject> boxes= new Hashtable<>();
//
//    private static int id = 1;
//
//    private static synchronized int generatedId(){
//        return id++;
//    }
//
//    public static GuardedObject getGuardedObject(int id){
//        return boxes.remove(id);
//    }
//
//    public static GuardedObject createGuardedObject(){
//        GuardedObject guardedObject = new GuardedObject();
//        guardedObject.setId(generatedId());
//        boxes.put(guardedObject.getId(),guardedObject);
//        return guardedObject;
//    }
//
//    public static Set<Integer> getIds(){
//        return boxes.keySet();
//    }
//}
//
//class GuardedObject{
//    private Integer id;
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    private Object response;
//
//    //timeout 就是最长等待时间
//    public Object get(long timeout){
//        synchronized (this){
//            long begin = System.currentTimeMillis();
//
//            long passTime = 0;
//
//            while (response == null){
//                if(passTime >= timeout){
//                    break;
//                }
//                try {
//                    this.wait(timeout- passTime);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                passTime = System.currentTimeMillis() - begin;
//            }
//        }
//        return response;
//    }
//
//    public void complete(Object response){
//        synchronized (this){
//            this.response =response;
//            this.notifyAll();
//        }
//    }
//}