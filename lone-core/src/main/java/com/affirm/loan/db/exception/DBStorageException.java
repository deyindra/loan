package com.affirm.loan.db.exception;

/**
 * An exception class For Database storage exception, also can be used for Referential integrity check
 */
public class DBStorageException extends RuntimeException {
    /**
     *
     * @param message Error Message
     */
    public DBStorageException(String message) {
        super(message);
    }

    /**
     *
     * @param message Error Message
     * @param cause Underlying exception
     */
    public DBStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
