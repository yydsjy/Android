package com.yyds.concurrent;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/***
 * COndition
 */
public class ReentrantLockDemo3 {
    static class ReentrantLockTask {
        private Condition worker1Condition, worker2Condition;
        ReentrantLock lock = new ReentrantLock(true);

        volatile int flag = 0;

        public ReentrantLockTask() {
            worker1Condition = lock.newCondition();
            worker2Condition = lock.newCondition();
        }

        void work1() {
            try {
                lock.lock();
                if (flag == 0 || flag % 2 == 0) {
                    System.out.println("Worker1 rest");
                    worker1Condition.await();
                }
                System.out.println("Worker1: " + flag);
                flag = 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        void work2() {
            try {
                lock.lock();
                if (flag == 0 || flag % 2 != 0) {
                    System.out.println("Worker2 rest");
                    worker2Condition.await();
                }
                System.out.println("Worker2: " + flag);
                flag = 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        void boss() {
            try {
                lock.lock();
                flag = new Random().nextInt(100);
                if (flag % 2 == 0) {
                    worker2Condition.signal();
                    System.out.println("Await worker 2: " + flag);
                } else {
                    worker1Condition.signal();
                    System.out.println("Await worker 1: " + flag);
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        final ReentrantLockTask task = new ReentrantLockTask();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    task.work1();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    task.work2();
                }
            }
        }).start();

        for (int i = 0; i < 10; i++) {
            task.boss();
        }

    }
}
