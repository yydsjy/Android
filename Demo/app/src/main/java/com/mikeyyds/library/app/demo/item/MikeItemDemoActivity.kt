package com.mikeyyds.library.app.demo.item

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikeyyds.library.app.R
import com.mikeyyds.ui.item.*
import com.mikeyyds.ui.item.core.MikeAdapter
import com.mikeyyds.ui.item.core.MikeDataItem

class MikeItemDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mike_item_demo)

        var mikeAdapter = MikeAdapter(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = mikeAdapter

        var dataSets = ArrayList<MikeDataItem<*, RecyclerView.ViewHolder>>()
        dataSets.add(TopTabDataItem(null))
        dataSets.add(BannerDataItem(null))
        dataSets.add(GridDataItem(null))
        dataSets.add(TabDataItem1(null))
        dataSets.add(TabDataItem2(null))

        for (i in 0..9) {
            if (i % 2 == 0) {
                dataSets.add(VideoDataItem(1, null))
            } else {
                dataSets.add(ImageDataItem(1, null))
            }
        }

// TODO: 2021-07-02
        mikeAdapter.addItems(dataSets, false)


    }
}