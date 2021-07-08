package com.yyds.concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/***
 * shared lock and exclusive lock
 */
public class ReentrantReadWriteLockDemo {
    static class ReentrantReadWriteLockTask{
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private final ReentrantReadWriteLock.WriteLock writeLock;
        private final ReentrantReadWriteLock.ReadLock readLock;

        ReentrantReadWriteLockTask(){
            writeLock = lock.writeLock();
            readLock = lock.readLock();
        }

        void read(){
            String name = Thread.currentThread().getName();
            try {
                readLock.lock();
                System.out.println("Read: "+name);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
                System.out.println("Read unlock: "+name);
            }
        }

        void write(){
            String name = Thread.currentThread().getName();
            try {
                writeLock.lock();
                System.out.println("Write: "+name);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
                System.out.println("Write unlock: "+name);
            }
        }

        public static void main(String[] args) {
            ReentrantReadWriteLockTask task = new ReentrantReadWriteLockTask();
            for (int i = 0; i < 3; i++) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        task.read();
                    }
                }).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        task.write();
                    }
                }).start();
            }
        }
    }
}
