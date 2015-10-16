package com.busylee.devpanel.info;

/**
 * Created by busylee on 16.10.15.
 */
public class ObjectInfo implements InfoEntry {

    private final Object mData;

    public ObjectInfo(Object data) {
        mData = data;
    }

    @Override
    public Object getData() {
        return mData;
    }
}
