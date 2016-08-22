package com.busylee.devpanel

import android.content.Context
import com.busylee.devpanel.info.ButtonInfo
import com.busylee.devpanel.info.InfoEntry
import com.busylee.devpanel.info.ObjectInfo
import com.busylee.devpanel.mutable.BooleanMutable
import com.busylee.devpanel.mutable.SetStringMutableEntry
import com.busylee.devpanel.mutable.StringMutable

/**
 * Created by busylee on 17.06.16.
 */
class MutableBuilderResolver(val context: Context) {

    fun bool(): BooleanAdder {
        return bool(false)
    }

    fun bool(value: Boolean): BooleanAdder {
        return BooleanAdder(context, value)
    }

    fun set(): StringSetAdder {
        return StringSetAdder(context)
    }

    fun set(default: String): StringSetAdder {
        return StringSetAdder(context).default(default)
    }

    fun edit(): StringAdder {
        return edit("")
    }

    fun edit(default: String): StringAdder {
        return StringAdder(context, default)
    }

    class BooleanAdder(context: Context, value: Boolean) : BooleanMutable.Builder(context, value) {

        override fun onChange(onChangeFun: (Boolean, Context?) -> Unit): BooleanAdder {
            super.onChange(onChangeFun)
            return this
        }

        override fun title(title: String): BooleanAdder {
            super.title(title)
            return this
        }

        override fun key(key: String): BooleanAdder {
            super.key(key)
            return this
        }

        fun add(): BooleanMutable {
            var mutable = build()
            DevPanel.addMutable(build())
            return mutable
        }
    }

    class StringSetAdder(context: Context) : SetStringMutableEntry.Builder(context) {
        override fun onChange(onChangeFun: (String, Context?) -> Unit)
                : StringSetAdder {
            super.onChange(onChangeFun)
            return this
        }

        override fun title(title: String): StringSetAdder {
            super.title(title)
            return this
        }

        override fun key(key: String): StringSetAdder {
            super.key(key)
            return this
        }

        override fun default(default: String): StringSetAdder {
            super.default(default)
            return this
        }

        override fun values(vararg values: String): StringSetAdder {
            super.values(*values)
            return this
        }

        fun add(): SetStringMutableEntry {
            var mutable = build()
            DevPanel.addMutable(build())
            return mutable
        }
    }

    class StringAdder(context: Context, default: String) : StringMutable.Builder(context, default) {
        override fun onChange(onChangeFun: (String, Context?) -> Unit): StringMutable.Builder {
            super.onChange(onChangeFun)
            return this
        }

        override fun title(title: String): StringAdder {
            super.title(title)
            return this
        }

        override fun key(key: String): StringAdder {
            super.key(key)
            return this
        }

        fun add(): StringMutable {
            val mutable = build()
            DevPanel.addMutable(mutable)
            return mutable
        }
    }

}