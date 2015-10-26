package com.busylee.devpanel.mutable

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by busylee on 23.10.15.
 */
class SetStringMutableEntry(
        context: Context,
        override val name: String, //used to store preference
        private val defaultValue: String,
        val availableValues: Set<String>,
        override val onChange : (String, context: Context? = null) -> Unit = { value, context -> }) : MutableEntry<String> () {

    constructor(context: Context,
                name: String, //used to store preference
                defaultKey: String,
                availableValues: Set<String>) : this(context, name, defaultKey, availableValues, { value, context -> })

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    override val data: String
        get() = sharedPreferences.getString(name, defaultValue)

    override fun change(newValue: String, context: Context?) {
        super.change(newValue, context)

        //to check value is available
        if(!availableValues.contains(newValue))
            throw IllegalArgumentException("There is no such available value");

        sharedPreferences.edit().putString(name, newValue).apply();
    }

}