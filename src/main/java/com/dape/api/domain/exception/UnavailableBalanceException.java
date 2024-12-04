package com.dape.api.domain.exception;

public class UnavailableBalanceException extends RuntimeException {
    public UnavailableBalanceException(String message) {
        super(message);
    }
}
