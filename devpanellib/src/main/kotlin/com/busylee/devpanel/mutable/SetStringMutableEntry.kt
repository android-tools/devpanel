package com.busylee.devpanel.mutable

import android.content.Context
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
        onChange : (String, Context?) -> Unit = { _, _ -> })
: MutableEntry<String> (onChange) {

    private val sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    override val data: String
        get() = sharedPreferences.getString(name, defaultValue)

    override fun onChange(newValue: String, context: Context?) {
        //to check value is available
        if(!availableValues.contains(newValue))
            throw IllegalArgumentException("There is no such available value")

        sharedPreferences.edit().putString(name, newValue).apply()
    }

    open class Builder(
            private val context: Context) {

        var key: String = ""
        var title: String = ""
        var default: String = ""
        var availableValues: Set<String>? = null

        var onChange : (String, Context?) -> Unit = { _, _ -> }

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
            if(key.isEmpty()) {
                throw IllegalArgumentException("Please specify key")
            }

            val availableValues = this.availableValues
            if(availableValues == null || availableValues.isEmpty()) {
                throw IllegalArgumentException("Please specify available values")
            }

            if(default.isEmpty()) {
                default = availableValues.first()
            }

            if(title.isEmpty()) {
                title = key
            }

            return SetStringMutableEntry(context, key, title, default, availableValues, onChange)
        }
    }

}