package com.yyds.concurrent;

import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.net.ssl.TrustManagerFactory;

public class AtomicDemo {

    public static void main(String[] args) throws InterruptedException {
        AtomicTask task = new AtomicTask();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println(Thread.currentThread().getName());
                    task.incrementAtomic();
                    task.incrementVolatile();
                }
            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println("amotic: "+task.atomicInteger.get());
        System.out.println("volatile: "+task.volatileCount);
    }
    static class AtomicTask{
        AtomicInteger atomicInteger = new AtomicInteger();
        volatile int volatileCount = 0;

        void incrementAtomic(){
            atomicInteger.getAndIncrement();
        }

        void incrementVolatile(){
            volatileCount++;
        }
    }
}
