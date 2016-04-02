package com.distance_tracker.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.distance_tracker.R;
import com.distance_tracker.events.TrackedInfoUpdatedEvent;
import com.distance_tracker.location.TrackedInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public abstract class BaseInfoActivity extends AppCompatActivity {

    @Bind(R.id.info)
    protected TextView infoView;

    EventBus eventBus = EventBus.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventBus.registerSticky(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }

    public void onEventMainThread(TrackedInfoUpdatedEvent event) {
        showTrackedInfo(event.getTrackedInfo());
    }

    protected abstract void showTrackedInfo(TrackedInfo info);
}
