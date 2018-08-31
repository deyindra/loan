package com.affirm.loan.db.lock;

import com.affirm.loan.ignore.IgnoreCoverage;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@IgnoreCoverage
public interface DBLock {
    ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
    Lock READ_LOCK = LOCK.readLock();
    Lock WRITE_LOCK = LOCK.writeLock();
}
