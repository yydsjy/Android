package com.mikeyyds.ui.item;

import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.mikeyyds.ui.R;
import com.mikeyyds.ui.item.core.MikeDataItem;

import org.jetbrains.annotations.NotNull;

public class TopTabDataItem extends MikeDataItem<Object, RecyclerView.ViewHolder> {

    public TopTabDataItem(Object data) {
        super(data);
    }

    @Override
    public void onBindData(@NotNull RecyclerView.ViewHolder holder, int position) {
        ImageView imageView = holder.itemView.findViewById(R.id.item_image);
        imageView.setImageResource(R.drawable.item_top_tab);
    }

    @Override
    public int getItemLayoutRes() {
        return R.layout.mike_item_top_tab;
    }
}
