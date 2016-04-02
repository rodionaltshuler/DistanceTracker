package com.distance_tracker.location;

import android.support.annotation.StringRes;

import com.distance_tracker.R;

public enum LocationUpdatesStatus {

    ON(R.string.stop_tracking) {
        @Override
        public LocationUpdatesStatus inverse() {
            return OFF;
        }
    },
    OFF(R.string.start_tracking) {
        @Override
        public LocationUpdatesStatus inverse() {
            return ON;
        }
    };

    private int buttonTitle;

    LocationUpdatesStatus(@StringRes int buttonTitle) {
        this.buttonTitle = buttonTitle;
    }

    public @StringRes int getButtonTitle() {
        return buttonTitle;
    }

    public abstract LocationUpdatesStatus inverse();

}
