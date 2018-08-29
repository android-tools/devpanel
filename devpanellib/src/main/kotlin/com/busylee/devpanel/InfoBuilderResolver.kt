package com.busylee.devpanel

import android.content.Context
import com.busylee.devpanel.info.ButtonInfo
import com.busylee.devpanel.info.MutableInfo
import com.busylee.devpanel.info.ObjectInfo
import com.busylee.devpanel.info.preferences.*

/**
 * Created by busylee on 05.07.16.
 */
class InfoBuilderResolver(val categoryManager: CategoryManager, val context: Context) {

    fun mutable(valueFunc: () -> Any): MutableInfoAdder {
        return MutableInfoAdder(categoryManager, valueFunc)
    }

    fun simple(value: String): ObjectInfoAdder {
        return ObjectInfoAdder(categoryManager, value)
    }

    fun button(): ButtonAdder {
        return ButtonAdder(categoryManager)
    }

    fun pref(): PreferencesInfoAdder {
        return PreferencesInfoAdder(categoryManager, context)
    }

    class ButtonAdder(private val categoryManager: CategoryManager) : ButtonInfo.Builder() {
        override fun onClick(onClick: (Context?) -> Unit): ButtonAdder {
            super.onClick(onClick)
            return this
        }

        override fun title(title: String): ButtonAdder{
            super.title(title)
            return this
        }

        @JvmOverloads
        fun add (categoryName: String? = null) {
            categoryManager.add(build(), categoryName)
        }
    }

    class ObjectInfoAdder(
            private val categoryManager: CategoryManager,
            value: Any
    ) : ObjectInfo.Builder(value) {

        override fun title(title: String): ObjectInfoAdder {
            super.title(title)
            return this
        }

        @JvmOverloads
        fun add (categoryName: String? = null) {
            categoryManager.add(build(), categoryName)
        }
    }

    class MutableInfoAdder(
            private val categoryManager: CategoryManager,
            valueFunc: () -> Any
    ) : MutableInfo.Builder(valueFunc) {

        override fun title(title: String): MutableInfoAdder {
            super.title(title)
            return this
        }

        @JvmOverloads
        fun add (categoryName: String? = null) {
            categoryManager.add(build(), categoryName)
        }
    }

    class PreferencesInfoAdder(
            private val categoryManager: CategoryManager,
            context: Context
    ): PreferenceInfo.Builder(context) {

        var infoBuilder: PreferenceInfo.Builder? = null

        fun bool(default: Boolean = false): PreferencesInfoAdder {
            infoBuilder = BooleanPreferenceInfo.Builder(context, title, preferenceKey).default(default)
            return this
        }

        fun float(default: Float = 0f): PreferencesInfoAdder {
            infoBuilder = FloatPreferenceInfo.Builder(context, title, preferenceKey).default(default)
            return this
        }

        fun integer(default: Int = 0): PreferencesInfoAdder {
            infoBuilder = IntPreferenceInfo.Builder(context, title, preferenceKey).default(default)
            return this
        }

        fun llong(default: Long = 0L): PreferencesInfoAdder {
            infoBuilder = LongPreferenceInfo.Builder(context, title, preferenceKey).default(default)
            return this
        }

        fun string(default: String = ""): PreferencesInfoAdder {
            infoBuilder = StringPreferenceInfo.Builder(context, title, preferenceKey).default(default)
            return this
        }

        override fun title(title: String): PreferencesInfoAdder {
            super.title(title)
            infoBuilder?.title(title)
            return this
        }

        override fun key(key: String): PreferencesInfoAdder {
            super.key(key)
            infoBuilder?.key(key)
            return this
        }

        @JvmOverloads
        fun add (categoryName: String? = null) {
            if(infoBuilder == null) {
                throw IllegalArgumentException("Please specify preference type")
            }

            checkAndThrow()
            categoryManager.add((infoBuilder as PreferenceInfo.Builder).build(), categoryName)
        }

        override fun build(): PreferenceInfo<Any> {
            throw UnsupportedOperationException()
        }
    }

}