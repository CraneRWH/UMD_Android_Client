package com.ymd.client.component.event;

public class LoginEvent {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public LoginEvent(boolean success) {
        this.success = success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
