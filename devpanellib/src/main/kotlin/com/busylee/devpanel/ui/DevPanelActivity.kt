package com.busylee.devpanel.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.busylee.devpanel.DevPanel

import com.busylee.devpanel.R
import com.busylee.devpanel.info.InfoEntry
import kotlinx.android.synthetic.a_dev_panel.*

public class DevPanelActivity : AppCompatActivity() {

    private var infoAdapter: InfoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_dev_panel)

        var infoMap = DevPanel.getInfoMap();

        for()

        infoAdapter = InfoListAdapter()

        initVies()
    }

    fun initVies() {

        lv_info_list
    }

}
