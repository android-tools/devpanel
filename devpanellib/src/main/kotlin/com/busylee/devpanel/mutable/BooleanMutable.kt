package com.busylee.devpanel.mutable;

import android.content.Context

/**
 * Created by busylee on 23.10.15.
 */
class BooleanMutable(
        context: Context,
        override val name: String,
        override val title: String,
        private val defaultValue: Boolean,
        onChange: (Boolean, context: Context?) -> Unit = { _, _ -> }
) : MutableEntry<Boolean>(onChange) {

    private val sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    override val data: Boolean
        get() = sharedPreferences.getBoolean(name, defaultValue)

    override fun onChange(newValue: Boolean, context: Context?) {
        sharedPreferences.edit().putBoolean(name, newValue).apply()
    }

    open class Builder(
            private val context: Context,
            private val value: Boolean) {

        var key: String = ""
        var title: String = ""
        var onChange : (Boolean, context: Context?) -> Unit = { _, _ -> }

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
            if(key.isEmpty()) {
                throw IllegalArgumentException("Please specify key")
            }

            if(title.isEmpty()) {
                title = key
            }

            return BooleanMutable(context, key, title, value)
        }
    }
}
