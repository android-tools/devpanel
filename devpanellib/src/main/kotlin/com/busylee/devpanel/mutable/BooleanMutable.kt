package com.busylee.devpanel.mutable;

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by busylee on 23.10.15.
 */
class BooleanMutable(
        context: Context,
        override val name: String,
        private val defaultValue: Boolean,
        override val onChange : (Boolean) -> Unit = { value -> }) : MutableEntry<Boolean>() {

    constructor(context: Context,
                name: String,
                defaultValue: Boolean) : this(context, name, defaultValue, { value -> })

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    override val data: Boolean
        get() = sharedPreferences.getBoolean(name, defaultValue)

    override fun change(newValue: Boolean) {
        super.change(newValue)
        sharedPreferences.edit().putBoolean(name, newValue).apply();
    }
}
