package com.busylee.devpanel.info.preferences

import android.content.SharedPreferences

/**
 * Created by busylee on 23.10.15.
 */

class BooleanPreferenceInfo(
    sharedPreferences: SharedPreferences,
    override val title: String,
    preferenceKey: String,
    val default: Boolean = false)
    : PreferenceInfo<Boolean>(sharedPreferences, title, preferenceKey) {

    override fun getDataFromPref(sharedPref: SharedPreferences, key: String): Boolean {
        return sharedPref.getBoolean(key, default)
    }

    open class Builder(
        sharedPreferences: SharedPreferences,
        title: String = "",
        preferenceKey: String = ""
    ) : PreferenceInfo.Builder(sharedPreferences, title, preferenceKey) {

        var default = false

        fun default(default: Boolean): Builder {
            this.default = default
            return this
        }

        override fun build(): BooleanPreferenceInfo {
            return BooleanPreferenceInfo(sharedPreferences, title, preferenceKey, default)
        }
    }
}