package com.mikeyyds.library.log;

import org.junit.Test;

import static org.junit.Assert.*;

public class MikeLogTest {

    @Test
    public void Testlog() {
        MikeLogManager.init(new MikeLogConfig() {
        });
        MikeLog.log(1,"mike","mike");
    }
}