package com.busylee.devpanel.info

/**
 * Created by busylee on 16.10.15.
 */
class ObjectInfo(
        override val data: Any,
        override val title: String,
        override val name: String = "") : InfoEntry<Any> {

    open class Builder(private val data: Any) {

        var title = ""

        open fun title(title: String): Builder {
            this.title = title
            return this
        }

        fun build(): ObjectInfo {
            return ObjectInfo(data, title)
        }

    }

}
