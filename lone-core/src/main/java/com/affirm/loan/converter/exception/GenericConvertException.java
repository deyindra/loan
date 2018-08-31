package com.affirm.loan.converter.exception;

/**
 * @author indranil dey
 * Generic Convert Exception class which will be used to throw any exception while converting into any generic object
 * @see RuntimeException
 */
public class GenericConvertException extends RuntimeException{
    /**
     *
     * @param message - Error Message
     */
    public GenericConvertException(String message) {
        super(message);
    }

    /**
     *
     * @param message - Error Message
     * @param cause - Underlying exception
     */
    public GenericConvertException(String message, Throwable cause) {
        super(message, cause);
    }
}
