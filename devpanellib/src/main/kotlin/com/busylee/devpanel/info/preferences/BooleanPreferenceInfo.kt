package com.busylee.devpanel.info.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by busylee on 23.10.15.
 */

class BooleanPreferenceInfo (
    override val title: String,
    preferenceKey: String,
    context: Context,
    val default: Boolean = false)
: PreferenceInfo<Boolean> (title, preferenceKey, context) {

    override fun getDataFromPref(sharedPref: SharedPreferences, key: String): Boolean {
        return sharedPref.getBoolean(key, default)
    }

    open class Builder(context: Context,
                       title:String = "",
                       preferenceKey: String = "") : PreferenceInfo.Builder(context, title, preferenceKey) {

        var default = false

        fun default(default: Boolean): Builder {
            this.default = default
            return this
        }

        override fun build() :BooleanPreferenceInfo {
            return BooleanPreferenceInfo(title, preferenceKey, context, default)
        }
    }
}