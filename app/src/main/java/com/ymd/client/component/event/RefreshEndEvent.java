package com.ymd.client.component.event;

public class RefreshEndEvent {
    boolean isFinish;

    public RefreshEndEvent(boolean isFinish) {
        this.isFinish = isFinish;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }
}
