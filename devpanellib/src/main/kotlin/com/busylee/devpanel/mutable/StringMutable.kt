package com.busylee.devpanel.mutable

import android.content.Context

/**
 * Created by busylee on 22.08.16.
 */
class StringMutable(
        context: Context,
        override val name: String,
        override val title: String,
        private val defaultValue: String,
        onChange: (String, context: Context?) -> Unit = { _, _ -> }
) : MutableEntry<String>(onChange) {

    private val sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    override val data: String
        get() = sharedPreferences.getString(name, defaultValue)

    override fun onChange(newValue: String, context: Context?) {
        sharedPreferences.edit().putString(name, newValue).apply()
    }

    open class Builder(
            private val context: Context,
            private val value: String) {

        var key = ""
        var title = ""
        var onChange: (String, context: Context?) -> Unit = { _, _ -> }

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
            if (key.isEmpty()) {
                throw IllegalArgumentException("Please specify key")
            }

            if (title.isEmpty()) {
                title = key
            }

            return StringMutable(context, key, title, value)
        }
    }
}