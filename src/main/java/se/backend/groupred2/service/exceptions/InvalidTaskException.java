package se.backend.groupred2.service.exceptions;

public final class InvalidTaskException extends RuntimeException {

    public InvalidTaskException(String message) {
        super(message);
    }
}
