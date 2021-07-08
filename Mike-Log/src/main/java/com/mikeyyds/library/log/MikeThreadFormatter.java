package com.mikeyyds.library.log;

public class MikeThreadFormatter implements MikeLogFormatter<Thread>{
    @Override
    public String format(Thread data) {
        return "Thread: "+ data.getName();
    }
}
