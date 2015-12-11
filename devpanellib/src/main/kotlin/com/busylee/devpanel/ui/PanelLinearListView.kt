package com.busylee.devpanel.ui

import android.content.Context
import android.database.DataSetObserver
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.ListAdapter
import com.busylee.devpanel.R

/**
 * Created by busylee on 07.05.15.

 */
class PanelLinearListView : LinearLayout, View.OnClickListener, View.OnLongClickListener {


    private val dataSetObserver = LinearLayoutDataSetObserver()
    private var adapter: ListAdapter? = null
    private var onItemClickListener: AdapterView.OnItemClickListener? = null
    private var onItemLongClickListener: AdapterView.OnItemLongClickListener? = null

    private var hasDivider: Boolean = false
    private var hasTopDivider: Boolean = false
    private var hasBottomDivider: Boolean = false
    private var dividerHeight: Int = 0
    private var dividerColor: Int = 0
    private var headerView: View? = null

    constructor(context: Context) : super(context, null) {
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this.orientation = LinearLayout.VERTICAL

        if (attrs != null) {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.PanelLinearListView)
            this.dividerColor = attributes.getColor(R.styleable.PanelLinearListView_dev_ll_dividerColor, 0)
            this.dividerHeight = attributes.getDimensionPixelSize(R.styleable.PanelLinearListView_dev_ll_dividerHeight, 0)
            if (this.dividerColor != 0 && this.dividerHeight != 0) {
                hasDivider = true
                hasTopDivider = attributes.getBoolean(R.styleable.PanelLinearListView_dev_ll_hasTopDivider, false)
                hasBottomDivider = attributes.getBoolean(R.styleable.PanelLinearListView_dev_ll_hasBottomDivider, false)
            }
            attributes.recycle()
        }
    }

    private fun createDivider(): View {
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dividerHeight)
        val dividerView = View(context)
        dividerView.layoutParams = params
        dividerView.setBackgroundColor(dividerColor)
        return dividerView
    }

    fun setHeaderView(view: View) {
        this.headerView = view
        inflateList()
    }

    fun setAdapter(adapter: ListAdapter?) {
        this.adapter?.unregisterDataSetObserver(this.dataSetObserver)

        this.adapter = adapter

        this.adapter?.registerDataSetObserver(this.dataSetObserver)

        inflateList()
    }

    fun setOnItemClickListener(onItemClickListener: AdapterView.OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: AdapterView.OnItemLongClickListener?) {
        this.onItemLongClickListener = onItemLongClickListener
    }

    private fun inflateList() {
        removeAllViews()

        if (this.headerView != null) {
            this.addView(this.headerView)
        }

        if (this.adapter == null) {
            return
        }
        val itemsCount = this.adapter!!.count

        if (itemsCount > 0 && this.hasTopDivider) {
            addView(createDivider())
        }
        for (position in 0..itemsCount - 1) {
            addItemView(this.adapter!!.getView(position, null, this), position)

            if (this.hasDivider && position != itemsCount - 1) {
                addView(createDivider())
            }
        }

        if (itemsCount > 0 && this.hasBottomDivider) {
            addView(createDivider())
        }
    }

    private fun addItemView(view: View, position: Int) {
        view.setTag(R.id.tag_position, position)
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
        addView(view)
    }

    override fun onClick(v: View) {
        if (this.onItemClickListener != null) {
            val position = v.getTag(R.id.tag_position) as Int
            val id = this.adapter!!.getItemId(position)
            this.onItemClickListener!!.onItemClick(null, v, position, id)
        }
    }

    override fun onLongClick(v: View): Boolean {
        if (this.onItemLongClickListener != null) {
            val position = v.getTag(R.id.tag_position) as Int
            val id = this.adapter!!.getItemId(position)
            return this.onItemLongClickListener!!.onItemLongClick(null, v, position, id)
        } else {
            return false
        }
    }

    private inner class LinearLayoutDataSetObserver : DataSetObserver() {
        override fun onChanged() {
            inflateList()
        }

        override fun onInvalidated() {
            inflateList()
        }
    }

}
