package com.affirm.loan.db;

public abstract class AbstractDBStorageTest {
    protected String file;

    protected AbstractDBStorageTest(String file) {
        this.file = file;
    }
}
