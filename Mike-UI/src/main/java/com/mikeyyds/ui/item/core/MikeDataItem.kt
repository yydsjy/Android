package com.mikeyyds.ui.item.core

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class MikeDataItem<DATA, VH:RecyclerView.ViewHolder>(data: DATA?) {
    private lateinit var adapter: MikeAdapter
    var mData: DATA? = null

    init {
        this.mData = data
    }

    abstract fun onBindData(holder: VH, position: Int)

    open fun getItemLayoutRes(): Int {
        return -1
    }

    open fun getItemView(parent: ViewGroup): View? {
        return null
    }

    open fun getSpanSize(): Int {
        return 0
    }

    fun setAdapter(adapter: MikeAdapter) {
        this.adapter = adapter
    }

    fun refreshItem() {
        adapter.refreshItem(this)
    }

    fun removeItem() {
        adapter.removeItem(this)
    }

}