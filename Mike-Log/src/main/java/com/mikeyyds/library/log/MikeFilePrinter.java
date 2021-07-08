package com.mikeyyds.library.log;

import androidx.annotation.NonNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class MikeFilePrinter implements MikeLogPrinter{
    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private final String logPath;
    private final long retentionTime;
    private LogWriter writer;
    private volatile PrintWorker worker;
    private static MikeFilePrinter instance;

    public static synchronized MikeFilePrinter getInstance(String logPath, long retentionTime) {
        if (instance == null) {
            instance = new MikeFilePrinter(logPath, retentionTime);
        }
        return instance;
    }


    private MikeFilePrinter(String logPath, long retentionTime) {
        this.logPath = logPath;
        this.retentionTime = retentionTime;
        this.writer = new LogWriter();
        this.worker = new PrintWorker();
        cleanExpiredLog();
    }



    @Override
    public void print(@NonNull MikeLogConfig config, int level, String tag, @NonNull String printString) {
        long timeMillis =System.currentTimeMillis();
        if (!worker.isRunning()) {
            worker.start();
        }
        worker.put(new MikeLogMo(timeMillis,level,tag,printString));
    }

    private class LogWriter {
        private String preFileName;
        private File logFile;
        private BufferedWriter bufferedWriter;

        boolean isReady() {return bufferedWriter!=null;}
        String getPreFileName(){return preFileName;}

        boolean prepare(String newFileName){
            preFileName = newFileName;
            logFile = new File(logPath,newFileName);

            if (!logFile.exists()) {
                try {
                    File parent= logFile.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    preFileName = null;
                    logFile = null;
                    return false;
                }
            }

            try {
                bufferedWriter = new BufferedWriter(new FileWriter(logFile,true));
            } catch (Exception e){
                e.printStackTrace();
                preFileName = null;
                logFile = null;
                return false;
            }
            return true;
        }

        boolean close(){
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    bufferedWriter = null;
                    preFileName = null;
                    logFile = null;
                }
            }
            return true;
        }

        void append(String flattenedLog){
            try {
                bufferedWriter.write(flattenedLog);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class PrintWorker implements Runnable{

        private BlockingDeque<MikeLogMo> logs = new LinkedBlockingDeque<>();
        private volatile boolean running;

        void put(MikeLogMo log){
            try {
                logs.put(log);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        boolean isRunning(){
            synchronized (this){
                return running;
            }
        }

        void start(){
            synchronized (this){
                EXECUTOR.execute(this);
                running = true;
            }
        }

        @Override
        public void run() {
            MikeLogMo log;
            try {
                while (true) {
                    log = logs.take();
                    doPrint(log);
                }
            } catch (Exception e) {
                e.printStackTrace();
                synchronized (this){
                    running = false;
                }
            }
        }
    }

    private void doPrint(MikeLogMo logMo) {
        String lastFileName = writer.getPreFileName();
        if (lastFileName == null) {
            String newFileName = genFileName();
            if (writer.isReady()) {
                writer.close();
            }
            if (!writer.prepare(newFileName)){
                return;
            }
        }
        writer.append(logMo.flattenedLog());
    }

    private String genFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    private void cleanExpiredLog() {
        if (retentionTime <= 0) return;
        long currentTimeMillis = System.currentTimeMillis();
        File logDir = new File(logPath);
        File[] files = logDir.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (currentTimeMillis - file.lastModified() > retentionTime) {
                file.delete();
            }
        }
    }
}
