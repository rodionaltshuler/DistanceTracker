package com.distance_tracker.events;

import com.distance_tracker.location.LocationUpdatesStatus;

public class TrackingStatusChangedEvent {

    private LocationUpdatesStatus status;

    public TrackingStatusChangedEvent(LocationUpdatesStatus status) {
        this.status = status;
    }

    public LocationUpdatesStatus getStatus() {
        return status;
    }
}
