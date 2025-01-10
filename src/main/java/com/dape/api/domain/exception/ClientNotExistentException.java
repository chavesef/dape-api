package com.dape.api.domain.exception;

public class ClientNotExistentException extends RuntimeException {
    public ClientNotExistentException(String message) {
        super(message);
    }
}
