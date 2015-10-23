package com.busylee.devpanel.info.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by busylee on 23.10.15.
 */

class BooleanPreferenceInfo(preferenceKey: String, context: Context, val default: Boolean = false) :
        PreferenceInfo<Boolean> (preferenceKey,context) {

        override fun getDataFromPref(sharedPref: SharedPreferences, key: String): Boolean {
                return sharedPref.getBoolean(key, default);
        }

}