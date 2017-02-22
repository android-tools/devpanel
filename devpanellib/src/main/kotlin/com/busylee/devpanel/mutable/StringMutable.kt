package com.busylee.devpanel.mutable

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils

/**
 * Created by busylee on 22.08.16.
 */
class StringMutable (context: Context,
    override val name: String,
    override val title: String,
    private val defaultValue: String,
    override val onChange : (String, context: Context?) -> Unit = { value, context -> }) : MutableEntry<String>() {

    constructor(context: Context,
            name: String,
            title: String,
            defaultValue: String) : this(context, name, title, defaultValue, { value, context -> })

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    override val data: String
    get() = sharedPreferences.getString(name, defaultValue)

    override fun change(newValue: String, context: Context?) {
        super.change(newValue, context)
        sharedPreferences.edit().putString(name, newValue).apply();
    }

    open class Builder(val context: Context, val value: String) {

        var key = ""
        var title = ""
        var onChange : (String, context: Context?) -> Unit = { value, context -> }

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

        fun build(): StringMutable {
            if(TextUtils.isEmpty(key)) {
                throw IllegalArgumentException("Please specify key")
            }

            if(TextUtils.isEmpty(title)) {
                title = key
            }

            return StringMutable(context, key, title, value)
        }
    }
}