package br.com.pedalise.pedalisebackend.user.exception;

public class InvalidUsernameException extends RuntimeException{

    public InvalidUsernameException(String message) {
        super(message);
    }
}
