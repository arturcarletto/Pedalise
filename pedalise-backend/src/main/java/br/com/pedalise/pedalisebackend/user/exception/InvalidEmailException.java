package br.com.pedalise.pedalisebackend.user.exception;

public class InvalidEmailException extends RuntimeException{

    public InvalidEmailException(String mensage) {
        super(mensage);
    }
}
