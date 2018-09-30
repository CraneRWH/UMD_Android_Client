package com.ymd.client.component.event;

public class LoginRefreshEvent {
    boolean isRefresh;

    public LoginRefreshEvent(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }
}
