package com.dape.api.domain.exception;

public class BetNotExistent extends RuntimeException {
    public BetNotExistent(String message) {
        super(message);
    }
}
