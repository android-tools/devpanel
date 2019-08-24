package com.busylee.devpanel.mutable;

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by busylee on 23.10.15.
 */
class BooleanMutable(
        private val sharedPreferences: SharedPreferences,
        override val name: String,
        override val title: String,
        private val defaultValue: Boolean,
        onChange: (Boolean, context: Context?) -> Unit = { _, _ -> }
) : MutableEntry<Boolean>(onChange) {

    override val data: Boolean
        get() = sharedPreferences.getBoolean(name, defaultValue)

    override fun onChange(newValue: Boolean, context: Context?) {
        sharedPreferences.edit().putBoolean(name, newValue).apply()
    }

    open class Builder(
            private val context: Context,
            private val value: Boolean) {

        private var key: String = ""
        private var title: String = ""
        private var onChange : (Boolean, context: Context?) -> Unit = { _, _ -> }

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

            return BooleanMutable(
                context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE),
                key,
                title,
                value
            )
        }
    }
}
