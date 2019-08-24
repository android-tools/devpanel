package com.busylee.devpanel.info.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by busylee on 23.10.15.
 */

class StringPreferenceInfo(
    sharedPreferences: SharedPreferences,
    override val title: String,
    preferenceKey: String,
    val default: String = ""
) : PreferenceInfo<String>(sharedPreferences, title, preferenceKey) {

    override fun getDataFromPref(sharedPref: SharedPreferences, key: String): String {
        return sharedPref.getString(key, default)!!
    }

    open class Builder(
        sharedPreferences: SharedPreferences,
        title: String = "",
        preferenceKey: String = ""
    ) : PreferenceInfo.Builder(sharedPreferences, title, preferenceKey) {

        var default = ""

        fun default(default: String): Builder {
            this.default = default
            return this
        }

        override fun build(): StringPreferenceInfo {
            return StringPreferenceInfo(sharedPreferences, title, preferenceKey, default)
        }
    }

}