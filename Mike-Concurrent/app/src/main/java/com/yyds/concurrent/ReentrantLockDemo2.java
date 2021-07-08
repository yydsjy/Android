package com.yyds.concurrent;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.locks.ReentrantLock;

/***
 * fair lock and unfair lock
 */
public class ReentrantLockDemo2 {
    static class ReentrantLockTask {
        ReentrantLock lock = new ReentrantLock(false);
        void print(){
            String name = Thread.currentThread().getName();
            try {
                lock.lock();
                System.out.println(name+": 1");
                Thread.sleep(1000);
                lock.unlock();

                lock.lock();
                System.out.println(name+": 2");


            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockTask task = new ReentrantLockTask();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                task.print();
            }
        };
        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }
}
