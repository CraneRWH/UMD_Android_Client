package com.ymd.client.component.event;

public class LocationFinishEvent {
    private boolean isFinish;

    public LocationFinishEvent(boolean isFinish) {
        this.isFinish = isFinish;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }
}
