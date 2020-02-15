package com.loyaltypartner.elok.quiz.storage;

public class StorageException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 2949003262109151662L;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
