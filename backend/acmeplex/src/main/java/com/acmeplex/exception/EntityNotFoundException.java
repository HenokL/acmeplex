package com.acmeplex.exception; 

public class EntityNotFoundException extends RuntimeException {
    
    // Default constructor
    public EntityNotFoundException() {
        super("Entity not found");
    }
    
    // Constructor with a custom message
    public EntityNotFoundException(String message) {
        super(message);
    }

    // Constructor that accepts both a message and a cause
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}