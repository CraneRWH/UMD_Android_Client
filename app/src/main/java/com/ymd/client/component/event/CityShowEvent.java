package com.ymd.client.component.event;

public class CityShowEvent {
    boolean isRefresh;

    public CityShowEvent(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }
}
