package com.ymd.client.component.event;

public class UEvent {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public UEvent(boolean success) {
        this.success = success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
