package com.yyds.concurrent;

import java.util.ArrayList;
import java.util.List;

public class SynchronizedDemo {
    static List<String> tickets = new ArrayList<>();

    public static void main(String[] args){
        for (int i = 0; i < 5; i++) {
            tickets.add("T "+i);
        }
        sellTickets();
    }

    static void sellTickets() {
        final Test test = new Test();
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    test.printThreadName();
                }
            }).start();
        }
    }

    static class Test{
       synchronized void printThreadName(){
            String name = Thread.currentThread().getName();
            System.out.println(name);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name+' '+tickets.remove(0));
        }
    }
}
