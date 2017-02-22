package com.busylee.devpanel.mutable;

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils

/**
 * Created by busylee on 23.10.15.
 */
class BooleanMutable(
        context: Context,
        override val name: String,
        override val title: String,
        private val defaultValue: Boolean,
        override val onChange : (Boolean, context: Context?) -> Unit = { value, context -> }) : MutableEntry<Boolean>() {

    constructor(context: Context,
                name: String,
                title: String,
                defaultValue: Boolean) : this(context, name, title, defaultValue, { value, context -> })

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    override val data: Boolean
        get() = sharedPreferences.getBoolean(name, defaultValue)

    override fun change(newValue: Boolean, context: Context?) {
        super.change(newValue, context)
        sharedPreferences.edit().putBoolean(name, newValue).apply();
    }

    open class Builder(val context: Context, val value: Boolean) {

        var key: String = ""
        var title: String = ""
        var onChange : (Boolean, context: Context?) -> Unit = { value, context -> }

        open fun onChange(onChangeFun: (Boolean, Context?) -> Unit): Builder {
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

        fun build(): BooleanMutable {
            if(TextUtils.isEmpty(key)) {
                throw IllegalArgumentException("Please specify key")
            }

            if(TextUtils.isEmpty(title)) {
                title = key
            }

            return BooleanMutable(context, key, title, value)
        }
    }
}
