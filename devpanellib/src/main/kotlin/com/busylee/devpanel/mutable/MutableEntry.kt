package com.busylee.devpanel.mutable;

import android.content.Context
import com.busylee.devpanel.info.InfoEntry

/**
 * Created by busylee on 23.10.15.
 */
abstract class MutableEntry<Data> (
        private val onChange : (Data, context: Context?) -> Unit
) : InfoEntry<Data> {

    fun change(newValue : Data, context: Context?) {
        onChange(newValue, context)
    }

    abstract fun onChange(newValue : Data, context: Context?)

    companion object {
        const val PREFERENCES_NAME = "DevPanel_MutableEntries"
    }

}
