package com.dape.api.domain.exception;

public class InvalidBetStatusException extends RuntimeException {
    public InvalidBetStatusException(String message) {
        super(message);
    }
}
