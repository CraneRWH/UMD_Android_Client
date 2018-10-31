package com.ymd.client.component.event;

public class OrderListRefreshEvent {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public OrderListRefreshEvent(boolean success) {
        this.success = success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
