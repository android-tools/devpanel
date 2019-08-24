package com.busylee.devpanel.info

/**
 * Created by busylee on 17.05.17.
 */
class MutableInfo(
    private val dataFunc: () -> Any,
    override val title: String,
    override val name: String = "") : InfoEntry<Any> {

    override val data: Any
        get() = dataFunc.invoke()

    open class Builder(private val dataFunc: () -> Any) {

        private var title = ""

        open fun title(title: String): Builder {
            this.title = title
            return this
        }

        fun build(): MutableInfo {
            return MutableInfo(dataFunc, title)
        }

    }

}