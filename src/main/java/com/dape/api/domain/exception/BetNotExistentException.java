package com.dape.api.domain.exception;

public class BetNotExistentException extends RuntimeException {
    public BetNotExistentException(String message) {
        super(message);
    }
}
