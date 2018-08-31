package com.affirm.loan.reader;



public abstract class AbstractStreamProcessorTest {
    protected String filePath;
    protected boolean isParallel;
    protected AbstractStreamProcessorTest(String filePath, boolean isParallel) {
        this.filePath = filePath;
        this.isParallel = isParallel;
    }
}
