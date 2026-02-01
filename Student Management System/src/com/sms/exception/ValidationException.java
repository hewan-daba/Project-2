package com.sms.exception;

/**
 * ValidationException
 *
 * Custom exception thrown when input validation fails.
 */
public class ValidationException extends RuntimeException {

    /**
     * Constructor with message.
     *
     * @param message Validation error message
     */
    public ValidationException(String message) {

        super(message);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message Validation error message
     * @param cause   Throwable cause
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

