package com.busylee.devpanel.info.preferences

import android.content.SharedPreferences

/**
 * Created by busylee on 23.10.15.
 */

class LongPreferenceInfo(
    sharedPreferences: SharedPreferences,
    override val title: String,
    preferenceKey: String,
    val default: Long = 0L)
    : PreferenceInfo<Long>(sharedPreferences, title, preferenceKey) {

    override fun getDataFromPref(sharedPref: SharedPreferences, key: String): Long {
        return sharedPref.getLong(key, default)
    }

    open class Builder(
        sharedPreferences: SharedPreferences,
        title: String = "",
        preferenceKey: String = ""
    ) : PreferenceInfo.Builder(sharedPreferences, title, preferenceKey) {

        var default = 0L

        fun default(default: Long): Builder {
            this.default = default
            return this
        }

        override fun build(): LongPreferenceInfo {
            return LongPreferenceInfo(sharedPreferences, title, preferenceKey, default)
        }
    }

}