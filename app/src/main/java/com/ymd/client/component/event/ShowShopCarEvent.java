package com.ymd.client.component.event;

public class ShowShopCarEvent {
    private boolean isShow;

    public ShowShopCarEvent(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
