package com.busylee.devpanel.info.preferences

import android.content.Context
import android.content.SharedPreferences
import com.busylee.devpanel.info.InfoEntry

/**
 * Created by busylee on 23.10.15.
 */
abstract class PreferenceInfo<out Data>(val preferenceKey: String, context: Context) : InfoEntry<Data> {

    val preferencesName: String = "preference_infos"
    val preferencesMode: Int = Context.MODE_PRIVATE
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(preferencesName, preferencesMode)

    override val data: Data
        get() = getDataFromPref(sharedPreferences, preferenceKey)

    override val name: String
        get() = preferenceKey

    abstract fun getDataFromPref(sharedPref : SharedPreferences, key: String) : Data

}