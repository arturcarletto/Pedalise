package br.com.pedalise.pedalisebackend.event.exception;

public class EventNotFoundException extends RuntimeException{

    public EventNotFoundException(String message) {
        super(message);
    }
}
