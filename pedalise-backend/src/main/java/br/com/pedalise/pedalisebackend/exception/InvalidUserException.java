package br.com.pedalise.pedalisebackend.exception;

public class InvalidUserException extends RuntimeException {

    public InvalidUserException(String message) {
        super(message);
    }

}