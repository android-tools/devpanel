package com.busylee.devpanel.info

/**
 * Created by busylee on 16.10.15.
 */
interface InfoEntry<out Data> {
    val name: String
    val title: String
    val data: Data
}
