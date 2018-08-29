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
class MutableBuilderResolver(
        private val categoryManager: CategoryManager,
        private val context: Context
) {

    fun bool(value: Boolean = false): BooleanAdder {
        return BooleanAdder(categoryManager, context, value)
    }

    fun set(): StringSetAdder {
        return StringSetAdder(categoryManager, context)
    }

    fun set(default: String? = null): StringSetAdder {
        return StringSetAdder(categoryManager, context).apply {
            default?.let { default(it) }
        }
    }

    fun edit(default: String = ""): StringAdder {
        return StringAdder(categoryManager, context, default)
    }

    class BooleanAdder(
            private val categoryManager: CategoryManager,
            context: Context,
            value: Boolean
    ) : BooleanMutable.Builder(context, value) {

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

        @JvmOverloads
        fun add(categoryName: String? = null): BooleanMutable {
            return build().apply {
                categoryManager.add(this, categoryName)
            }
        }
    }

    class StringAdder(
            private val categoryManager: CategoryManager,
            context: Context,
            default: String
    ) : StringMutable.Builder(context, default) {
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

        @JvmOverloads
        fun add(categoryName: String? = null): StringMutable {
            return build().apply {
                categoryManager.add(this, categoryName)
            }
        }
    }

    class StringSetAdder(
            private val categoryManager: CategoryManager,
            context: Context
    ) : SetStringMutableEntry.Builder(context) {
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

        @JvmOverloads
        fun add(categoryName: String? = null): SetStringMutableEntry {
            return build().apply {
                categoryManager.add(this, categoryName)
            }
        }
    }

}