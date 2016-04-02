package com.distance_tracker.ui;

import android.app.Activity;
import android.content.Intent;

import com.distance_tracker.BuildConfig;
import com.distance_tracker.location.TrackedInfo;

public class DistanceActivity extends BaseInfoActivity {

    public static void start(Activity src) {
        Intent i = new Intent(src, DistanceActivity.class);
        src.startActivity(i);
    }

    @Override
    protected void showTrackedInfo(TrackedInfo info) {
        infoView.setText(BuildConfig.FORMATTER.getFormatted(getResources(), info.getDistance()));
    }
}
