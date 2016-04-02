package com.distance_tracker.events;

import com.distance_tracker.location.TrackedInfo;

public class TrackedInfoUpdatedEvent {

    private TrackedInfo trackedInfo;

    public TrackedInfoUpdatedEvent(TrackedInfo trackedInfo) {
        this.trackedInfo = trackedInfo;
    }

    public TrackedInfo getTrackedInfo() {
        return trackedInfo;
    }
}
