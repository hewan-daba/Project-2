package com.sms.exception;

/**
 * DataAccessException
 *
 * Custom exception thrown when a database or DAO operation fails.
 */
public class DataAccessException extends RuntimeException {

    /**
     * Constructor with message.
     *
     * @param message Error message
     */
    public DataAccessException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message Error message
     * @param cause   Throwable cause
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
