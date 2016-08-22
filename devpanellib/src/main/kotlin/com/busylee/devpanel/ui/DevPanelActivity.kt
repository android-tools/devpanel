package com.busylee.devpanel.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.busylee.devpanel.DevPanel

import com.busylee.devpanel.R
import com.busylee.devpanel.mutable.BooleanMutable
import com.busylee.devpanel.mutable.SetStringMutableEntry
import com.busylee.devpanel.mutable.StringMutable
import kotlinx.android.synthetic.main.a_dev_panel.*


class DevPanelActivity : AppCompatActivity() {

    private var infoAdapter: ListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_dev_panel)

        initializeViews()
    }

    fun initializeViews() {
        infoAdapter = InfoListAdapter(DevPanel.getInfoList(), this)
        linear_list_view.setAdapter(infoAdapter)

        var mutable = DevPanel.getMutableSet()
        for(element in mutable) {
            if(element is SetStringMutableEntry) {
                 addStringMutableView(element)
            }

            if(element is BooleanMutable) {
                addBooleanMutable(element)
            }

            if(element is StringMutable) {
                addStringMutable(element)
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

    fun addStringMutable(stringMutable: StringMutable) {
        val mutableView = layoutInflater.inflate(R.layout.i_string_mutable_value, null)

        val tvName = mutableView.findViewById(R.id.tv_name) as TextView
        tvName.text = stringMutable.name

        val etValue = mutableView.findViewById(R.id.et_value) as EditText
        etValue.setText(stringMutable.data)
        etValue.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                stringMutable.change(s.toString(), this@DevPanelActivity)
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        addToMutableContainer(mutableView)
    }

    fun addToMutableContainer(view: View) {
        ll_mutable_container.addView(view)
    }

}
