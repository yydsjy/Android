package com.mikeyyds.library.log;


import androidx.annotation.NonNull;

public interface MikeLogPrinter {
    void print(@NonNull MikeLogConfig config, int level, String tag, @NonNull String printString);
}
