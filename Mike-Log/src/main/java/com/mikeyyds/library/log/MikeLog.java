package com.mikeyyds.library.log;



import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

public class MikeLog {

    private static final String MIKE_LOG_PACKAGE;
    static {
        String className = MikeLog.class.getName();
        MIKE_LOG_PACKAGE = className.substring(0,className.lastIndexOf('.')+1);
    }
    public static void v(Object... contents) {
        log(MikeLogType.V, contents);
    }

    public static void vt(String tag, Object... contents) {
        log(MikeLogType.V, tag, contents);
    }

    public static void d(Object... contents) {
        log(MikeLogType.D, contents);
    }

    public static void dt(String tag, Object... contents) {
        log(MikeLogType.D, tag, contents);
    }

    public static void i(Object... contents) {
        log(MikeLogType.I, contents);
    }

    public static void it(String tag, Object... contents) {
        log(MikeLogType.I, tag, contents);
    }

    public static void w(Object... contents) {
        log(MikeLogType.W, contents);
    }

    public static void wt(String tag, Object... contents) {
        log(MikeLogType.W, tag, contents);
    }

    public static void e(Object... contents) {
        log(MikeLogType.E, contents);
    }

    public static void et(String tag, Object... contents) {
        log(MikeLogType.E, tag, contents);
    }

    public static void a(Object... contents) {
        log(MikeLogType.A, contents);
    }

    public static void at(String tag, Object... contents) {
        log(MikeLogType.A, tag, contents);
    }

    public static void log(@MikeLogType.TYPE int type, Object... contents) {
        log(type,MikeLogManager.getInstance().getConfig().getGlobalTag(),contents);
    }

    public static void log(@MikeLogType.TYPE int type, @NonNull String tag, Object... contents) {
        log(MikeLogManager.getInstance().getConfig(),type,tag,contents);
    }

    public static void log(@NonNull MikeLogConfig config, @MikeLogType.TYPE int type, @NonNull String tag, Object... contents) {
        if (!config.enable()){
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (config.includeThread()){
            String threadInfo = MikeLogConfig.MIKE_THREAD_FORMATTER.format(Thread.currentThread());
            sb.append(threadInfo).append("\n");
        }
        if (config.stackTraceDepth()>0){
            String stackTrace = MikeLogConfig.MIKE_STACK_TRACE_FORMATTER.format(
                    MikeStackTraceUtil.getCroppedRealStackTrack(new Throwable().getStackTrace(),MIKE_LOG_PACKAGE,config.stackTraceDepth()));
            sb.append(stackTrace).append("\n");
        }
        String body=parseBody(contents,config);
        sb.append(body);

        List<MikeLogPrinter> printers = config.printers()!=null? Arrays.asList(config.printers()):MikeLogManager.getInstance().getPrinters();
        if (printers==null){
            return;
        }
        for (MikeLogPrinter printer:printers){
            printer.print(config,type,tag,sb.toString());
        }
    }

    private static String parseBody(@NonNull Object[] contents,@NonNull MikeLogConfig config){
        if (config.injectParser()!=null){
            return config.injectParser().toJson(contents);
        }
        StringBuilder sb = new StringBuilder();
        for (Object content : contents) {
            sb.append(content.toString()).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
}
