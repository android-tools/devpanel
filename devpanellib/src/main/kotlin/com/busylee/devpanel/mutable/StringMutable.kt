package com.busylee.devpanel.mutable

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by busylee on 22.08.16.
 */
class StringMutable(
        private val sharedPreferences: SharedPreferences,
        override val name: String,
        override val title: String,
        private val defaultValue: String,
        onChange: (String, context: Context?) -> Unit = { _, _ -> }
) : MutableEntry<String>(onChange) {

    override val data: String
        get() = sharedPreferences.getString(name, defaultValue)!!

    override fun onChange(newValue: String, context: Context?) {
        sharedPreferences.edit().putString(name, newValue).apply()
    }

    open class Builder(
            private val context: Context,
            private val value: String) {

        private var key = ""
        private var title = ""
        private var onChange: (String, context: Context?) -> Unit = { _, _ -> }

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

            return StringMutable(
                context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE),
                key,
                title,
                value
            )
        }
    }
}