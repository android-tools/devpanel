package com.busylee.devpanel.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.busylee.devpanel.R
import com.busylee.devpanel.info.InfoEntry

/**
 * Created by busylee on 16.10.15.
 */
class InfoListAdapter(val list : List<InfoEntry<*>>, context: Context) : BaseAdapter() {

    internal final val layoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return list.size()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View? {
        var resultView : View;

        if(view == null) {
            resultView = layoutInflater.inflate(R.layout.i_info_item, viewGroup, false);
        } else {
            resultView = view;
        }

        bindView(resultView, position)

        return resultView;
    }

    internal fun bindView(view: View, position: Int) {
        val tvInfoEntryName = view.findViewById(R.id.tv_info_entry_name) as TextView
        val tvInfoEntryValue = view.findViewById(R.id.tv_info_entry_value) as TextView

        val data = getItem(position);

        tvInfoEntryName.text = data.name;
        tvInfoEntryValue.text = data.data.toString()
    }

    override fun getItem(position: Int): InfoEntry<*> {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0;
    }

}
