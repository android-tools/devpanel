package com.busylee.devpanel.mutable

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import java.util.*
import kotlin.collections.Set
/**
 * Created by busylee on 23.10.15.
 */
class SetStringMutableEntry(
        context: Context,
        override val name: String,
        override val title: String,
        private val defaultValue: String,
        val availableValues: Set<String>,
        override val onChange : (String, Context?) -> Unit = { value, context -> })
: MutableEntry<String> () {

    constructor(context: Context,
                name: String,
                title: String,
                defaultKey: String,
                availableValues: Set<String>)
    : this(context, name, title, defaultKey, availableValues, { value, context -> })

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

    open class Builder(val context: Context) {

        var key: String = ""
        var title: String = ""
        var default: String = ""
        var availableValues: Set<String>? = null

        var onChange : (String, Context?) -> Unit = { value, context -> }

        open fun onChange(onChangeFun: (String, Context?) -> Unit): Builder {
            onChange = onChangeFun
            return this
        }

        open fun title(title: String): Builder {
            this.title = title
            return this
        }

        open fun key(key: String): Builder {
            this.key = key
            return this
        }

        open fun default(default: String): Builder {
            this.default = default
            return this
        }

        open fun values(vararg values: String): Builder {
            val available = HashSet<String>()
            available.addAll(values)
            this.availableValues = available
            return this
        }

        fun build(): SetStringMutableEntry {
            if(TextUtils.isEmpty(key)) {
                throw IllegalArgumentException("Please specify key")
            }

            if(availableValues == null) {
                throw IllegalArgumentException("Please secify available values")
            }

            if(TextUtils.isEmpty(default)) {
                default = (availableValues as Set<String>).first()
            }

            return SetStringMutableEntry(context, key, title, default, availableValues as Set<String>, onChange)
        }
    }

}