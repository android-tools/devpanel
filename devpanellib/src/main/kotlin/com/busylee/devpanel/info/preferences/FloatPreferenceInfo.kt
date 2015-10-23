package com.busylee.devpanel.info.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by busylee on 23.10.15.
 */

class FloatPreferenceInfo(preferenceKey: String, context: Context, val default: Float = 0f) :
        PreferenceInfo<Float> (preferenceKey,context) {

        override fun getDataFromPref(sharedPref: SharedPreferences, key: String): Float {
                return sharedPref.getFloat(key, default);
        }

}