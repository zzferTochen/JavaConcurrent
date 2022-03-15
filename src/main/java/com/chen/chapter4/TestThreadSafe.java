package com.chen.chapter4;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huchen
 * @description
 * @since 2022/2/28 13:20
 */
public class TestThreadSafe {
    public static void main(String[] args) {
        UnsafeTest unsafeTest = new UnsafeTest();
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                unsafeTest.method1();
            },"线程"+i).start();
        }
    }
}



class UnsafeTest{
    public void method1(){
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            method2(arrayList);
            method3(arrayList);
        }
    }


    public void method2(ArrayList arrayList){
        arrayList.add("1");
    }
    public void method3(ArrayList arrayList){
        arrayList.remove(0);
    }
}

class ThreadSafeSubClass extends UnsafeTest{
    @Override
    public void method3(ArrayList arrayList){
        new Thread(()->{
            arrayList.remove(0);
        });
    }
}