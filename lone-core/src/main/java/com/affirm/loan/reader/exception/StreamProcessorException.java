package com.affirm.loan.reader.exception;

/**
 * @author indranil dey
 * Generic exception class for Stream Processor
 */
public class StreamProcessorException extends RuntimeException {
    public StreamProcessorException(String message) {
        super(message);
    }

    public StreamProcessorException(String message, Throwable cause) {
        super(message, cause);
    }
}
