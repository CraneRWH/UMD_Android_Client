package com.ymd.client.component.event;

public class RefreshMerchantListEvent {
    boolean isRefresh;
    int page = 0;
    long pid;

    public RefreshMerchantListEvent(boolean isRefresh, long pid) {
        this.isRefresh = isRefresh;
        this.pid = pid;
    }

    public RefreshMerchantListEvent(boolean isRefresh, long pid, int page) {
        this.isRefresh = isRefresh;
        this.pid = pid;
        this.page = page;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }
}
