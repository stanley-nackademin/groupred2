package se.backend.groupred2.service.exceptions;

public class InvalidTeamException extends RuntimeException {

    public InvalidTeamException(String message) {
        super(message);
    }
}
