package com.dape.api.adapter.dto.response;

public class ErrorResponse {
    private final String message;
    private final Integer errorCode;

    public ErrorResponse(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
