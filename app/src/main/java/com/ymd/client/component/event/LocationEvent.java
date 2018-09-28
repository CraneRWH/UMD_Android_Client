package com.ymd.client.component.event;

import com.ymd.client.model.bean.city.LocationInfoEntity;

public class LocationEvent {

    private boolean isLocation;
    private LocationInfoEntity locationInfo;

    public LocationEvent(boolean isLocation, LocationInfoEntity locationInfo) {
        this.isLocation = isLocation;
        this.locationInfo = locationInfo;
    }

    public LocationInfoEntity getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(LocationInfoEntity locationInfo) {
        this.locationInfo = locationInfo;
    }

    public boolean isLocation() {
        return isLocation;
    }

    public void setLocation(boolean location) {
        isLocation = location;
    }
}
