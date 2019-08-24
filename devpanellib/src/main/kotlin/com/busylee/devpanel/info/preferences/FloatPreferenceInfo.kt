package com.busylee.devpanel.info.preferences

import android.content.SharedPreferences

/**
 * Created by busylee on 23.10.15.
 */

class FloatPreferenceInfo(
    sharedPreferences: SharedPreferences,
    override val title: String,
    preferenceKey: String,
    val default: Float = 0f)
    : PreferenceInfo<Float>(sharedPreferences, title, preferenceKey) {

    override fun getDataFromPref(sharedPref: SharedPreferences, key: String): Float {
        return sharedPref.getFloat(key, default)
    }

    open class Builder(
        sharedPreferences: SharedPreferences,
        title: String = "",
        preferenceKey: String = ""
    ) : PreferenceInfo.Builder(sharedPreferences, title, preferenceKey) {

        var default = 0f

        fun default(default: Float): Builder {
            this.default = default
            return this
        }

        override fun build(): FloatPreferenceInfo {
            return FloatPreferenceInfo(sharedPreferences, title, preferenceKey, default)
        }
    }

}