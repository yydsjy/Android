package com.yyds.concurrent;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import javax.security.auth.login.LoginException;

public class ConcurrentDemo {
    public static final String TAG = "ConcurrentTest";
    public static final int MSG_WHAT_1 = 1;

    public static volatile boolean hasNotify;

    public static void test(Context context) {

        /***
         * 1 AsyncTask
         */

/*        class MyAsyncTask extends AsyncTask<String,Integer,String>{

            @Override
            protected String doInBackground(String... strings) {
                for (int i = 0;i<10;i++){
                    publishProgress(i*10);
                }
                return strings[0];
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                Log.e(TAG, "onProgressUpdate: "+values[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                Log.e(TAG, "onPostExecute: "+s );
            }
        }

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("execute myAsyncTask");



        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: AsyncTask execute" );
            }
        });

        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: THREAD_POOL_EXECUTOR" );
            }
        });

        for (int i = 0; i < 10; i++) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "run: "+ System.currentTimeMillis());
                }
            });
        }*/


        /***
         * 2 HandlerThread
         */

/*        HandlerThread handlerThread = new HandlerThread("handler thread");
        handlerThread.start();

        MyHandler myHandler = new MyHandler(handlerThread.getLooper());
        myHandler.sendEmptyMessage(MSG_WHAT_1);*/

        /***
         * 3 wait notify
         */

/*        final Object object = new Object();
        class Runnable1 implements Runnable {

            @Override
            public void run() {
                Log.e(TAG, "run: thread1 start");
                synchronized (object) {
                    try {
                        if (!hasNotify) {
                            object.wait(5000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.e(TAG, "run: thread1 end");
            }
        }

        class Runnable2 implements Runnable{
            @Override
            public void run() {
                Log.e(TAG, "run: thread2 start" );
                synchronized (object){
                    object.notify();
                    hasNotify = true;
                }
                Log.e(TAG, "run: thread2 end" );
            }
        }

        Thread thread1 = new Thread(new Runnable1());
        Thread thread2 = new Thread(new Runnable2());
        thread1.start();
        thread2.start();*/

        /***
         * 4 join
         */

/*        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: 1" + System.currentTimeMillis());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "run: 2" + System.currentTimeMillis());
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "test: 3" + System.currentTimeMillis());*/

        /***
         * 5 sleep
         */

/*        final Object object = new Object();
        class Runnable1 implements Runnable {

            @Override
            public void run() {
                Log.e(TAG, "run: thread1 start");
                synchronized (object) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.e(TAG, "run: thread1 end");
            }
        }

        class Runnable2 implements Runnable{
            @Override
            public void run() {
                synchronized (object){
                    Log.e(TAG, "run: thread2 start");
                    Log.e(TAG, "run: thread2 end");
                }
            }
        }

        Thread thread1 = new Thread(new Runnable1());
        Thread thread2 = new Thread(new Runnable2());
        thread1.start();
        thread2.start();*/

        /***
         * 6 main thread send message to child thread
         */

/*        class LooperThread extends Thread{
            private Looper looper;

            public LooperThread(String name) {
                super(name );
            }

            public Looper getLooper(){
                synchronized (this){
                    if (looper == null && isAlive()) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return looper;
            }

            @Override
            public void run() {
                Looper.prepare();
                synchronized (this){
                    looper = Looper.myLooper();
                    notify();
                }
                Looper.loop();
            }
        }

        LooperThread looperThread = new LooperThread("Looper Thread");
        looperThread.start();
        Handler handler = new Handler(looperThread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.e(TAG, "handleMessage: "+msg.what );
                Log.e(TAG, "handleMessage: "+Thread.currentThread().getName() );
            }
        };

        handler.sendEmptyMessage(MSG_WHAT_1);*/


    }

/*    static class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.e(TAG, "handleMessage: "+msg.what );
            Log.e(TAG, "handleMessage: "+Thread.currentThread().getName() );
        }
    }*/
}
