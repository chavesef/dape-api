package com.dape.api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BetPostException extends IllegalArgumentException {
    public BetPostException(String message) {
        super(message);
    }
}
