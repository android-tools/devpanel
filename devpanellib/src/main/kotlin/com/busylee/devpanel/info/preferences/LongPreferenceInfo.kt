package com.busylee.devpanel.info.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by busylee on 23.10.15.
 */

class LongPreferenceInfo(preferenceKey: String, context: Context, val default: Long = 0L) :
        PreferenceInfo<Long> (preferenceKey,context) {

        override fun getDataFromPref(sharedPref: SharedPreferences, key: String): Long {
                return sharedPref.getLong(key, default);
        }

}