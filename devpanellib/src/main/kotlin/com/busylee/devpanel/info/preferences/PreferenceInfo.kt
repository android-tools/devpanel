package com.busylee.devpanel.info.preferences

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.busylee.devpanel.info.InfoEntry

/**
 * Created by busylee on 23.10.15.
 */
abstract class PreferenceInfo<out Data>(
        override val title: String,
        val preferenceKey: String,
        context: Context)
: InfoEntry<Data> {

    val preferencesName: String = "preference_infos"
    val preferencesMode: Int = Context.MODE_PRIVATE
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(preferencesName, preferencesMode)

    override val data: Data
        get() = getDataFromPref(sharedPreferences, preferenceKey)

    override val name: String
        get() = preferenceKey

    abstract fun getDataFromPref(sharedPref : SharedPreferences, key: String) : Data

    abstract open class Builder(val context: Context,
                                var title:String = "",
                                var preferenceKey: String = "") {

        open fun title(title: String): Builder {
            this.title = title
            return this
        }

        open fun key(key: String): Builder {
            this.preferenceKey = key
            return this
        }

        fun checkAndThrow() {
            if(TextUtils.isEmpty(preferenceKey)) {
                throw IllegalArgumentException("Preference key must be specified")
            }
        }

        abstract fun build(): PreferenceInfo<Any>

    }

}