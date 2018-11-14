package com.ymd.client.component.event;

public class LocationPermissionEvent {
    private boolean isGetPermission;
    private boolean isPermission;

    public LocationPermissionEvent(boolean isGetPermission, boolean isPermission) {
        this.isGetPermission = isGetPermission;
        this.isPermission = isPermission;
    }

    public boolean isGetPermission() {
        return isGetPermission;
    }

    public void setGetPermission(boolean getPermission) {
        isGetPermission = getPermission;
    }

    public boolean isPermission() {
        return isPermission;
    }

    public void setPermission(boolean permission) {
        isPermission = permission;
    }
}
