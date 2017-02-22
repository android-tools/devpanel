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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by busylee on 14.10.15.
 */
public class DevPanel implements ShakeDetector.OnShakeListener {

    private final Context mContext;
    private ShakeDetector mShakeDetector;
    private ArrayList<InfoEntry> mInfoList = new ArrayList<>();
    private Set<MutableEntry> mMutable = new LinkedHashSet<>();

    private static DevPanel sInstance;

    public DevPanel(Context context) {
        mContext = context;
    }

    public static void init(Context context) {
        sInstance = new DevPanel(context);
    }

    /**
     * =====
     * Facades methods to get mutables
     * =====
     */
    public static ArrayList<InfoEntry> getInfoList() {
        checkInitAndThrow();
        return getPanel().mInfoList;
    }

    public static Set<MutableEntry> getMutableSet() {
        checkInitAndThrow();
        return getPanel().mMutable;
    }

    /**
     * =====
     * Facades methods to create and add mutables infos and buttons
     * =====
    */
    public static MutableBuilderResolver mutable() {
        return getMutableResolver();
    }

    public static InfoBuilderResolver.ButtonAdder button() {
        return getInfoBuilderResolver().button();
    }

    public static InfoBuilderResolver info() {
        return getInfoBuilderResolver();
    }

    /**======*/

    private static MutableBuilderResolver getMutableResolver() {
        checkInitAndThrow();
        return new MutableBuilderResolver(sInstance.mContext);
    }

    private static InfoBuilderResolver getInfoBuilderResolver() {
        checkInitAndThrow();
        return new InfoBuilderResolver(sInstance.mContext);
    }

    private static DevPanel getPanel() {
        checkInitAndThrow();
        return sInstance;
    }

    private static void checkInitAndThrow() {
        if(sInstance == null) {
            throw new IllegalStateException("Panel has not been initialized");
        }
    }

    static void addMutable(MutableEntry entry) {
        checkInitAndThrow();
        getPanel().mMutable.add(entry);
    }

    static void addInfo(String title, Object object) {
        checkInitAndThrow();
        addInfo(new ObjectInfo(object, title, ""));
    }

    static void addInfo(InfoEntry infoEntry) {
        checkInitAndThrow();
        getPanel().mInfoList.add(infoEntry);
    }

    /**
    * =====
    * Shake detection
    * =====
    */

    public static void onResume(Context context) {
        checkInitAndThrow();
        getPanel().registerDetector(context);
    }

    public static void onPause(Context context) {
        checkInitAndThrow();
        getPanel().unregisterDetector(context);
    }

    private void registerDetector(Context context) {
        // ShakeDetector initialization
        final SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        final Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(getShakeDetector(), accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    private void unregisterDetector(Context context) {
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

    /**=====*/

    /**
    * Start DevPanel activity directly
    * @param context
    */
    public static void startDevPanel(Context context) {
        Intent intent = new Intent(context, DevPanelActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
