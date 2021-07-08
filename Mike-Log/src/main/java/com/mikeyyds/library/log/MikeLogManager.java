package com.mikeyyds.library.log;

import androidx.annotation.NonNull;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MikeLogManager {
    private MikeLogConfig config;
    private static MikeLogManager instance;
    private List<MikeLogPrinter> printers = new ArrayList<>();

    private MikeLogManager(MikeLogConfig config, MikeLogPrinter[] printers) {
        this.config = config;
        this.printers.addAll(Arrays.asList(printers));
    }

    public static MikeLogManager getInstance(){
        return instance;
    }

    public static void init(@NonNull MikeLogConfig config, MikeLogPrinter...printers){
        instance = new MikeLogManager(config,printers);
    }

    public MikeLogConfig getConfig(){
        return config;
    }

    public List<MikeLogPrinter> getPrinters() {
        return printers;
    }

    public void addPrinter(MikeLogPrinter printer){
        printers.add(printer);
    }

    public void removePrinter(MikeLogPrinter printer){
        if (printers!=null){
            printers.remove(printer);
        }
    }
}
