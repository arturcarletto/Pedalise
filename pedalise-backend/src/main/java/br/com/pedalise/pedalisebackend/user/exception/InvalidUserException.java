package br.com.pedalise.pedalisebackend.user.exception;

public class InvalidUserException extends RuntimeException {

    public InvalidUserException(String message) {
        super(message);
    }

}