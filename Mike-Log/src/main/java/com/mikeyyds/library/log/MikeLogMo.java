package com.mikeyyds.library.log;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MikeLogMo {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.CANADA);
    public long timeMills;
    public int level;
    public String tag;
    public String log;

    public MikeLogMo(long timeMills, int level, String tag, String log) {
        this.timeMills = timeMills;
        this.level = level;
        this.tag = tag;
        this.log = log;
    }

    public String flattenedLog(){
        return getFlattened()+"\n"+log;
    }

    public String getFlattened(){
        return format(timeMills)+'|'+level+'|'+tag+"|:";
    }

    public String format(long timeMills){
        return sdf.format(timeMills);
    }
}
