package br.com.pedalise.pedalisebackend.security.service.exception;

public class AuthenticationException extends RuntimeException{

    public AuthenticationException(String message) {
        super(message);
    }
}
