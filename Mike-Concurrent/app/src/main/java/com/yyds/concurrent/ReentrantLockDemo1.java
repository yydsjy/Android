package com.yyds.concurrent;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo1 {
    static class ReentrantLockTask{
        ReentrantLock reentrantLock = new ReentrantLock();
        void buyTicket(){
            String name = Thread.currentThread().getName();
            try {
                reentrantLock.lock();
                System.out.println(name+": 1");
                Thread.sleep(100);
                System.out.println(name+": 2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockTask task = new ReentrantLockTask();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                task.buyTicket();
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }
}
