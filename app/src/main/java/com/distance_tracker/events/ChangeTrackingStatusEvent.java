package com.distance_tracker.events;

import com.distance_tracker.location.LocationUpdatesStatus;

public class ChangeTrackingStatusEvent {

    private LocationUpdatesStatus status;

    public ChangeTrackingStatusEvent(LocationUpdatesStatus status) {
        this.status = status;
    }

    public LocationUpdatesStatus getStatus() {
        return status;
    }
}
