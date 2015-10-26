package com.busylee.devpanel.mutable

import android.content.Context
import com.busylee.devpanel.info.InfoEntry

/**
 * Created by busylee on 23.10.15.
 */
abstract class MutableEntry<Data> constructor(open val onChange : (Data, context: Context? = null) -> Unit = { value, context -> }) : InfoEntry<Data> {

    public open fun change(newValue : Data, context: Context?) : Unit {
        onChange(newValue, context);
    }

}
