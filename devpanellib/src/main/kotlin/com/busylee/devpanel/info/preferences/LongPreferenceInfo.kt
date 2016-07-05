package com.busylee.devpanel.info.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by busylee on 23.10.15.
 */

class LongPreferenceInfo(
    override val title: String,
    preferenceKey: String,
    context: Context,
    val default: Long = 0L)
: PreferenceInfo<Long> (title, preferenceKey, context) {

    override fun getDataFromPref(sharedPref: SharedPreferences, key: String): Long {
            return sharedPref.getLong(key, default);
    }

    open class Builder(context: Context) : PreferenceInfo.Builder(context) {

        var default = 0L

        fun default(default: Long): Builder {
            this.default = default
            return this
        }

        override fun build(): LongPreferenceInfo {
            return LongPreferenceInfo(title, preferenceKey, context, default)
        }
    }

}