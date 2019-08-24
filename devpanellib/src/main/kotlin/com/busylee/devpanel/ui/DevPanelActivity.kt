package com.busylee.devpanel.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.busylee.devpanel.DevPanel

import com.busylee.devpanel.R
import com.busylee.devpanel.info.Category
import com.busylee.devpanel.mutable.BooleanMutable
import com.busylee.devpanel.mutable.SetStringMutableEntry
import com.busylee.devpanel.mutable.StringMutable
import kotlinx.android.synthetic.main.a_dev_panel.*


class DevPanelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_dev_panel)

        initializeViews()
    }

    private fun initializeViews() {
        addCategory(DevPanel.getRootCategory(), true)
    }

    private fun addCategory(category: Category, showMutableTitles: Boolean = false) {
        val categoryContainer = addCategoryContainer(category.name)
        val infosLabel = categoryContainer.findViewById<View>(R.id.tv_info_label)
        val mutablesLabel = categoryContainer.findViewById<View>(R.id.tv_mutables_label)
        val infos = category.getInfos()
        val infoAdapter = InfoListAdapter(infos, this)
        categoryContainer.findViewById<PanelLinearListView>(R.id.linear_list_view)
                .setAdapter(infoAdapter)

        val mutableEntries = category.getMutableEntries()
        mutableEntries.forEach {
            when (it) {
                is SetStringMutableEntry -> {
                    addStringMutableView(it, categoryContainer)
                }
                is BooleanMutable -> {
                    addBooleanMutable(it, categoryContainer)
                }
                is StringMutable -> {
                    addStringMutable(it, categoryContainer)
                }
            }

            addDelimiter(categoryContainer)
        }

        if(showMutableTitles || infos.size >= ENTRIES_COUNT || mutableEntries.size >= ENTRIES_COUNT) {
            infosLabel.visibility = View.VISIBLE
            mutablesLabel.visibility = View.VISIBLE
        }

        category.categories.forEach {
            addCategory(it)
        }
    }

    private fun addCategoryContainer(name: String): ViewGroup = layoutInflater.inflate(
            R.layout.include_category_layout,
            main_container,
            false
    ).let { it as ViewGroup }.apply {
        main_container.addView(this)
        val tvCategoryName = findViewById<TextView>(R.id.tv_category_name)
        if (name != DevPanel.ROOT_CATEGORY_NAME) {
            tvCategoryName.text = name
        } else {
            tvCategoryName.visibility = View.GONE
        }
    }

    private fun addBooleanMutable(booleanMutable: BooleanMutable, container: ViewGroup) {
        val mutableView = layoutInflater.inflate(R.layout.i_boolean_mutable_value, container, false)

        val tvName = mutableView.findViewById(R.id.tv_name) as TextView
        tvName.text = booleanMutable.title

        val toggleButton = mutableView.findViewById(R.id.boolean_toogle_button) as ToggleButton
        toggleButton.isChecked = booleanMutable.data

        toggleButton.setOnCheckedChangeListener { _, b -> booleanMutable.change(b, this) }

        addToMutableContainer(mutableView, container)
    }

    private fun addStringMutableView(setStringMutableEntry: SetStringMutableEntry, container: ViewGroup) {
        val mutableView = layoutInflater.inflate(R.layout.i_set_string_mutable_value, container, false)

        val tvName = mutableView.findViewById(R.id.tv_name) as TextView
        tvName.text = setStringMutableEntry.title

        val tvValue = mutableView.findViewById(R.id.tv_value) as TextView
        tvValue.text = setStringMutableEntry.data

        val buttonsContainer = mutableView.findViewById(R.id.ll_buttons_container) as ViewGroup
        for (value in setStringMutableEntry.availableValues) {
            val button = Button(this)
            button.text = value
            button.setOnClickListener {
                tvValue.text = value
                setStringMutableEntry.change(value, this)
            }

            buttonsContainer.addView(button)
        }

        addToMutableContainer(mutableView, container)

    }

    private fun addStringMutable(stringMutable: StringMutable, container: ViewGroup) {
        val mutableView = layoutInflater.inflate(R.layout.i_string_mutable_value, container, false)

        val tvName = mutableView.findViewById(R.id.tv_name) as TextView
        tvName.text = stringMutable.title

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

        addToMutableContainer(mutableView, container)
    }

    private fun addDelimiter(container: ViewGroup) {
        addToMutableContainer(
                layoutInflater.inflate(
                        R.layout.v_delimiter,
                        container,
                        false
                ),
                container
        )
    }

    private fun addToMutableContainer(view: View, container: ViewGroup) {
        container.addView(view)
    }

    companion object {
        const val ENTRIES_COUNT = 3
    }

}
