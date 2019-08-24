package com.busylee.devpanel.info

import android.content.Context

/**
 * Created by busylee on 04.07.16.
 */
class ButtonInfo(
        override val title: String,
        val onClick : (context: Context?) -> Unit = { _ -> },
        override val name: String = "",
        override val data: Any = "") : InfoEntry<Any> {

    open class Builder {

        private var title = "Button"

        private var onClick: (context: Context?) -> Unit = { _ -> }

        open fun onClick(onClick: (Context?) -> Unit): Builder {
            this.onClick = onClick
            return this
        }

        open fun title(title: String): Builder {
            this.title = title
            return this
        }

        fun build(): ButtonInfo {
            return ButtonInfo(title, onClick)
        }
    }
}