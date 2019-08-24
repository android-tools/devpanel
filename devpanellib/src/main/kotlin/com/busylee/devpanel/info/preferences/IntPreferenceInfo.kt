package com.busylee.devpanel.info.preferences

import android.content.SharedPreferences

/**
 * Created by busylee on 23.10.15.
 */

class IntPreferenceInfo(
    sharedPreferences: SharedPreferences,
    override val title: String,
    preferenceKey: String,
    val default: Int = 0)
    : PreferenceInfo<Int>(sharedPreferences, title, preferenceKey) {

    override fun getDataFromPref(sharedPref: SharedPreferences, key: String): Int {
        return sharedPref.getInt(key, default)
    }

    open class Builder(
        sharedPreferences: SharedPreferences,
        title: String = "",
        preferenceKey: String = ""
    ) : PreferenceInfo.Builder(sharedPreferences, title, preferenceKey) {

        var default = 0

        fun default(default: Int): Builder {
            this.default = default
            return this
        }

        override fun build(): IntPreferenceInfo {
            return IntPreferenceInfo(sharedPreferences, title, preferenceKey, default)
        }
    }

}