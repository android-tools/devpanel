package com.busylee.devpanel;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.busylee.devpanel.mutable.MutableEntry;
import com.busylee.devpanel.info.InfoEntry;
import com.busylee.devpanel.info.ObjectInfo;
import com.busylee.devpanel.shake.ShakeDetector;
import com.busylee.devpanel.ui.DevPanelActivity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by busylee on 14.10.15.
 */
public class DevPanel implements ShakeDetector.OnShakeListener {

    private Context mContext;
    private ShakeDetector mShakeDetector;

    private Map<String, InfoEntry> mInfoMap = new HashMap<>();
    private Set<MutableEntry> mMutable = new HashSet<>();

    private static DevPanel sInstance;

    private static DevPanel getPanel() {
        if(sInstance == null) {
            sInstance = new DevPanel();
        }

        return sInstance;
    }

    public static Map<String, InfoEntry> getInfoMap() {
        return getPanel().mInfoMap;
    }

    public static Set<MutableEntry> getMutableSet() {
        return getPanel().mMutable;
    }

    public static void addMutable(MutableEntry entry) {
        getPanel().mMutable.add(entry);
    }

    public static void addInfo(String key, Object object) {
        addInfo(key, new ObjectInfo(object, key));
    }

    public static void addInfo(String key, InfoEntry infoEntry) {
        getPanel().mInfoMap.put(key, infoEntry);
    }

    public static void onResume(Context context) {
        getPanel().registerDetector(context);
    }

    public static void onPause(Context context) {
        getPanel().unregisterDetector(context);
    }

    void registerDetector(Context context) {
        mContext = context;
        // ShakeDetector initialization
        final SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        final Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(getShakeDetector(), accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    private void unregisterDetector(Context context) {
        mContext = null;
        final SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.unregisterListener(getShakeDetector());
    }

    private ShakeDetector getShakeDetector() {
        if(mShakeDetector == null) {
            mShakeDetector = new ShakeDetector();
            mShakeDetector.setOnShakeListener(this);
        }

        return mShakeDetector;
    }

    @Override
    public void onShake(int count) {
        startDevPanel(mContext);
    }

    public static void startDevPanel(Context context) {
        context.startActivity(new Intent(context, DevPanelActivity.class));
    }
}
