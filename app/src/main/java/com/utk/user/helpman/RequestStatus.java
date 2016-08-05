package com.utk.user.helpman;

/**
 * Created by user on 14-03-2016.
 */
public enum RequestStatus {
    RECEIVED("RECEIVED"),
    PROCESSING("PROCESSING"),
    CLOSED("CLOSED"),
    CANCELLED("CANCELLED");

    private String status;
    RequestStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return status;
    }
}
