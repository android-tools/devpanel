package com.busylee.devpanelsample;

import android.app.Application;

import com.busylee.devpanel.DevPanel;
import com.busylee.devpanel.info.preferences.IntPreferenceInfo;
import com.busylee.devpanel.mutable.BooleanMutable;
import com.busylee.devpanel.mutable.SetStringMutableEntry;

import java.util.HashSet;

/**
 * Created by busylee on 23.10.15.
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // simple object info
        DevPanel.addInfo("App name", getString(R.string.app_name));
        // preference info
        DevPanel.addInfo("Int pref example", new IntPreferenceInfo("int_key", this, 100));

        //String set
        HashSet<String> map = new HashSet<>();
        map.add("test");
        map.add("prod");
        DevPanel.addMutable(new SetStringMutableEntry(this, "host", "prod", map));

        //Boolean mutable
        DevPanel.addMutable(new BooleanMutable(this, "use test environment ", false));
    }
}
