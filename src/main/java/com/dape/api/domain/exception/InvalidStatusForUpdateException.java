package com.dape.api.domain.exception;

public class InvalidStatusForUpdateException extends RuntimeException {
    public InvalidStatusForUpdateException(String message) {
        super(message);
    }
}