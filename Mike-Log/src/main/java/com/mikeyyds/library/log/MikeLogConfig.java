package com.mikeyyds.library.log;

import kotlin.reflect.KVariance;

public abstract class MikeLogConfig {
    static int MAX_LEN = 512;
    static MikeStackTraceFormatter MIKE_STACK_TRACE_FORMATTER=new MikeStackTraceFormatter();
    static MikeThreadFormatter MIKE_THREAD_FORMATTER = new MikeThreadFormatter();
    public String getGlobalTag(){
        return "MikeLog";
    }
    public boolean enable(){
        return true;
    }
    public boolean includeThread(){
        return false;
    }
    public int stackTraceDepth(){
        return 5;
    }
    public MikeLogPrinter[] printers(){
        return null;
    }
    public interface JsonParser {
        String toJson(Object src);
    }

    public JsonParser injectParser() {
        return null;
    }
}
