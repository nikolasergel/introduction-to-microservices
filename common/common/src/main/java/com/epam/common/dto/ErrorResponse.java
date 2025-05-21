package com.epam.common.dto;

import java.util.Collections;
import java.util.Map;

public class ErrorResponse {
    private String errorMessage;
    private Map<String, String> details;
    private int errorCode;

    public ErrorResponse() {
    }

    public ErrorResponse(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.details = Collections.emptyMap();
        this.errorCode = errorCode;
    }

    public ErrorResponse(String errorMessage, Map<String, String> details, int errorCode) {
        this.errorMessage = errorMessage;
        this.details = details;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}