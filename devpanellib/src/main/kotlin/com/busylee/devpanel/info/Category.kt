package com.busylee.devpanel.info

import com.busylee.devpanel.mutable.MutableEntry

/**
 * Utility class, to provide ability to collect info entries in categories
 */
class Category @JvmOverloads constructor(
    val name: String,
    val categories: MutableList<Category> = mutableListOf(),
    val entries: MutableList<InfoEntry<*>> = mutableListOf(),
    var collapsible: Boolean = false,
    var collapsedByDefault: Boolean = false
) {
    fun getInfos() = entries.filter {
        it !is MutableEntry
    }

    fun getMutableEntries() = entries.filter {
        it is MutableEntry
    }
}