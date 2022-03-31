package com.chen.chapter6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huchen
 * @description 使用synchronized 来实现多线程访问共享来保证多线程访问共享资源发生的安全问题
 * @since 2022/3/31 15:16
 */
public class Test1 {
    public static void main(String[] args) {
        Account.demo(new AccountUnsafe(10000));
        Account.demo(new AccountCAS(10000));
    }
}
class AccountCAS implements Account{

    private AtomicInteger balance;

    public AccountCAS(int balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
        // 通过这里加锁就可以实现线程安全，不加就会导致线程安全问题
        while(true){
            int prev = balance.get();

            int next = prev -amount;

            if(balance.compareAndSet(prev,next)){
                break;
            }
        }
    }
}
class AccountUnsafe implements Account{

    private Integer balance;

    public AccountUnsafe(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        synchronized (this) {
            return balance;
        }
    }

    @Override
    public void withdraw(Integer amount) {
        // 通过这里加锁就可以实现线程安全，不加就会导致线程安全问题
        synchronized (this) {
            balance -= amount;
        }
    }
}
interface Account{

    /**
     * 获取余额
     * @return
     */
    Integer getBalance();

    /**
     * 取款
     * @param amount
     */
     void withdraw(Integer amount);


    /**
     * Java 8 之后接口新特性，可以添加默认方法
     * default 关键字修饰 或者 static 关键字修饰
     * 不需要实现接口的类重写该方法
     * @param account
     */
    static void demo(Account account){
        List<Thread> ts = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(()->{
                account.withdraw(10);
            }));
        }
        ts.forEach(thread -> thread.start());
        ts.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(account.getBalance()
                + " cost: " + (end - start) / 1000_000 + " ms");
    }
}