package com.busylee.devpanel.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListAdapter
import android.widget.TextView
import android.widget.ToggleButton
import com.busylee.devpanel.DevPanel

import com.busylee.devpanel.R
import com.busylee.devpanel.mutable.BooleanMutable
import com.busylee.devpanel.mutable.SetStringMutableEntry
import kotlinx.android.synthetic.a_dev_panel.*


public class DevPanelActivity : AppCompatActivity() {

    private var infoAdapter: ListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_dev_panel)

        initializeViews()
    }

    fun initializeViews() {
        var infoMap = DevPanel.getInfoMap().map {
            entry -> entry.value
        }

        infoAdapter = InfoListAdapter(infoMap, this)
        linear_list_view.setAdapter(infoAdapter);

        var mutable = DevPanel.getMutableSet();
        for(element in mutable) {
            if(element is SetStringMutableEntry) {
                 addStringMutableView(element)
            }

            if(element is BooleanMutable) {
                addBooleanMutable(element)
            }
        }
    }

    fun addBooleanMutable(booleanMutable: BooleanMutable ) {
        val mutableView = layoutInflater.inflate(R.layout.i_boolean_mutable_value, null)

        val tvName = mutableView.findViewById(R.id.tv_name) as TextView
        tvName.text = booleanMutable.name;

        val toggleButton = mutableView.findViewById(R.id.boolean_toogle_button) as ToggleButton
        toggleButton.isChecked = booleanMutable.data

        toggleButton.setOnCheckedChangeListener { compoundButton, b -> booleanMutable.change(b, this) }

        addToMutableContainer(mutableView)
    }

    fun addStringMutableView(setStringMutableEntry: SetStringMutableEntry) {
        val mutableView = layoutInflater.inflate(R.layout.i_set_string_mutable_value, null)

        val tvName = mutableView.findViewById(R.id.tv_name) as TextView
        tvName.text = setStringMutableEntry.name;

        val tvValue = mutableView.findViewById(R.id.tv_value) as TextView
        tvValue.text = setStringMutableEntry.data;

        val buttonsContainer = mutableView.findViewById(R.id.ll_buttons_container) as ViewGroup
        for(value in setStringMutableEntry.availableValues) {
            val button = Button(this)
            button.text = value;
            button.setOnClickListener({
                view ->
                    tvValue.text = value
                setStringMutableEntry.change(value, this)
            })

            buttonsContainer.addView(button)
        }

        addToMutableContainer(mutableView)

    }

    fun addToMutableContainer(view: View) {
        ll_mutable_container.addView(view)
    }

}
