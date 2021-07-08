package com.mikeyyds.ui.item

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mikeyyds.ui.R
import com.mikeyyds.ui.item.core.MikeDataItem

class TabDataItem2(data:Any?):
    MikeDataItem<Any, RecyclerView.ViewHolder>(data) {

    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        val imageView  = holder.itemView.findViewById<ImageView>(R.id.item_image)
        imageView.setImageResource(R.drawable.item_tab)
    }

    override fun getItemLayoutRes(): Int {
        return R.layout.mike_item_tab2
    }
}