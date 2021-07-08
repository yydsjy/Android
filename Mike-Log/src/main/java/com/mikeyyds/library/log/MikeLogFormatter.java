package com.mikeyyds.library.log;

public interface MikeLogFormatter<T> {
    String format(T data);
}
