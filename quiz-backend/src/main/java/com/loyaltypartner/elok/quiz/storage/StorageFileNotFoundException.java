package com.loyaltypartner.elok.quiz.storage;

public class StorageFileNotFoundException extends StorageException {

    /**
     * 
     */
    private static final long serialVersionUID = -9143354391562646920L;

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
