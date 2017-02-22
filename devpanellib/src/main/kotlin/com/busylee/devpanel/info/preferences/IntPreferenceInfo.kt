package com.busylee.devpanel.info.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by busylee on 23.10.15.
 */

class IntPreferenceInfo(
    override val title: String,
    preferenceKey: String,
    context: Context,
    val default: Int = 0)
: PreferenceInfo<Int> (title, preferenceKey, context) {

    override fun getDataFromPref(sharedPref: SharedPreferences, key: String): Int {
            return sharedPref.getInt(key, default);
    }

    open class Builder(context: Context,
                       title:String = "",
                       preferenceKey: String = "") : PreferenceInfo.Builder(context, title, preferenceKey) {

        var default = 0

        fun default(default: Int): Builder {
            this.default = default
            return this
        }

        override fun build(): IntPreferenceInfo {
            return IntPreferenceInfo(title, preferenceKey, context, default)
        }
    }

}