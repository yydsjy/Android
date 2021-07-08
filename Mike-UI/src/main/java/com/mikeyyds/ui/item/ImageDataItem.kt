package com.mikeyyds.ui.item

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mikeyyds.ui.R
import com.mikeyyds.ui.item.core.MikeDataItem

class ImageDataItem(spanCount:Int, data:Any?):
    MikeDataItem<Any, RecyclerView.ViewHolder>(data) {

    var spanCount:Int

    init {
        this.spanCount = spanCount
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        val imageView  = holder.itemView.findViewById<ImageView>(R.id.item_image)
        imageView.setImageResource(R.drawable.item_image)
    }

    override fun getItemLayoutRes(): Int {
        return R.layout.mike_item_image
    }

    override fun getSpanSize(): Int {
        return spanCount
    }
}