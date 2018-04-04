package com.busylee.devpanel.info.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by busylee on 23.10.15.
 */

class FloatPreferenceInfo(
    override val title: String,
    preferenceKey: String,
    context: Context,
    val default: Float = 0f)
: PreferenceInfo<Float> (title, preferenceKey, context) {

    override fun getDataFromPref(sharedPref: SharedPreferences, key: String): Float {
            return sharedPref.getFloat(key, default)
    }

    open class Builder(context: Context,
                       title:String = "",
                       preferenceKey: String = "") : PreferenceInfo.Builder(context, title, preferenceKey) {

        var default = 0f

        fun default(default: Float): Builder {
            this.default = default
            return this
        }

        override fun build(): FloatPreferenceInfo {
            return FloatPreferenceInfo(title, preferenceKey, context, default)
        }
    }

}