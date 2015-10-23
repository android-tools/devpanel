package com.busylee.devpanel.mutable

import com.busylee.devpanel.info.InfoEntry

/**
 * Created by busylee on 23.10.15.
 */
abstract class MutableEntry<Data> constructor(open val onChange : (Data) -> Unit = { value -> }) : InfoEntry<Data> {

    public open fun change(newValue : Data) : Unit {
        onChange(newValue);
    }

}
