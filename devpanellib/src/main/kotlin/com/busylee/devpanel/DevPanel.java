package com.busylee.devpanel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.busylee.devpanel.info.Category;
import com.busylee.devpanel.mutable.MutableEntry;
import com.busylee.devpanel.info.InfoEntry;
import com.busylee.devpanel.info.ObjectInfo;
import com.busylee.devpanel.shake.ShakeDetector;
import com.busylee.devpanel.ui.DevPanelActivity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by busylee on 14.10.15.
 */
public class DevPanel implements ShakeDetector.OnShakeListener {

    public static final String ROOT_CATEGORY_NAME = "DevPanel_root_category_name";

    @SuppressLint("StaticFieldLeak")
    private static DevPanel sInstance;

    private final Context mContext;
    private ShakeDetector mShakeDetector;
    private final CategoryManager mCategoryManager = new CategoryManager(this);

    @Deprecated
    private ArrayList<InfoEntry> mInfoList = new ArrayList<>();
    @Deprecated
    private Set<MutableEntry> mMutable = new LinkedHashSet<>();
    @Deprecated
    private Set<Category> mCategories = new LinkedHashSet<>();

    Category mRootCategory = new Category(ROOT_CATEGORY_NAME);

    public DevPanel(Context context) {
        mContext = context;
    }

    public static void init(Context context) {
        sInstance = new DevPanel(context);
    }

    /**
     * =====
     * Get root category outside
     * =====
     */

    public static Category getRootCategory() {
        checkInitAndThrow();
        return getPanel().mRootCategory;
    }

    /*======*/

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

    private static MutableBuilderResolver getMutableResolver() {
        checkInitAndThrow();
        return new MutableBuilderResolver(getManager(), sInstance.mContext);
    }

    private static InfoBuilderResolver getInfoBuilderResolver() {
        checkInitAndThrow();
        return new InfoBuilderResolver(getManager(), sInstance.mContext);
    }

    /**
     * =====
     * Util internal methods
     * =====
     */
    private static DevPanel getPanel() {
        checkInitAndThrow();
        return sInstance;
    }

    private static CategoryManager getManager() {
        checkInitAndThrow();
        return getPanel().mCategoryManager;
    }

    private static void checkInitAndThrow() {
        if(sInstance == null) {
            throw new IllegalStateException("Panel has not been initialized");
        }
    }

    /*=====*/

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

    /*=====*/

    /**
    * Start DevPanel activity directly
    * @param context
    */
    public static void startDevPanel(Context context) {
        Intent intent = new Intent(context, DevPanelActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /*=====*/

}
