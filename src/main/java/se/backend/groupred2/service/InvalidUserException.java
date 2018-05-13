package se.backend.groupred2.service;


public final class InvalidUserException extends RuntimeException {

    public InvalidUserException(String message) {
        super(message);
    }
}