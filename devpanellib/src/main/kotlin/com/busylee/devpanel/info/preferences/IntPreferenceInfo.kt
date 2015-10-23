package com.busylee.devpanel.info.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by busylee on 23.10.15.
 */

class IntPreferenceInfo(preferenceKey: String, context: Context, val default: Int = 0) :
        PreferenceInfo<Int> (preferenceKey,context) {

        override fun getDataFromPref(sharedPref: SharedPreferences, key: String): Int {
                return sharedPref.getInt(key, default);
        }

}