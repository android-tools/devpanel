package com.busylee.devpanel.info.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by busylee on 23.10.15.
 */

class StringPreferenceInfo(preferenceKey: String, context: Context, val default: String = "") :
        PreferenceInfo<String> (preferenceKey,context) {

    override fun getDataFromPref(sharedPref: SharedPreferences, key: String): String {
        return sharedPref.getString(key, default);

    }

}