package com.example.thehealingmeal.member.execption;

public class EntityNotFoundException extends RuntimeException {

    private final Exception hiddenException;

    public EntityNotFoundException(String message) {
        super(message);
        this.hiddenException = null;
    }

    public EntityNotFoundException(String entityName, Object keyValue, Exception exception) {
        super(entityName + " with id " + keyValue + " not found. Additional info: " + exception.getMessage());
        this.hiddenException = exception;
    }

    public Exception getHiddenException() {
        return hiddenException;
    }
}

