package com.mikeyyds.ui.tab.top;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mikeyyds.ui.R;
import com.mikeyyds.ui.tab.top.MikeTabTopInfo;
import com.mikeyyds.ui.tab.common.IMikeTab;

public class MikeTabTop extends RelativeLayout implements IMikeTab<MikeTabTopInfo<?>> {
    private MikeTabTopInfo<?> tabInfo;
    private ImageView tabImageView;
    private TextView tabNameView;
    private View indicator;

    public MikeTabTop(Context context) {
        this(context,null);
    }

    public MikeTabTop(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MikeTabTop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.mike_tab_top,this);
        tabImageView = findViewById(R.id.iv_image);
        tabNameView = findViewById(R.id.tv_name);
        indicator = findViewById(R.id.tab_top_indicator);
    }

    public MikeTabTopInfo<?> getTabInfo() {
        return tabInfo;
    }

    public ImageView getTabImageView() {
        return tabImageView;
    }

    public TextView getTabNameView() {
        return tabNameView;
    }

    @Override
    public void setMikeTabInfo(@NonNull MikeTabTopInfo data) {
        this.tabInfo = data;
        inflateInfo(false,true);
    }

    private void inflateInfo(boolean selected,boolean init) {
        if (tabInfo.tabType== MikeTabTopInfo.TabType.TEXT){
            if (init){
                tabImageView.setVisibility(GONE);
                tabNameView.setVisibility(VISIBLE);
                if (!TextUtils.isEmpty(tabInfo.name)){
                    tabNameView.setText(tabInfo.name);
                }
            }
            if (selected){
                indicator.setVisibility(VISIBLE);
                tabNameView.setTextColor(getTextColor(tabInfo.tintColor));
            } else {
                indicator.setVisibility(GONE);
                tabNameView.setTextColor(getTextColor(tabInfo.defaultColor));
            }
        } else if (tabInfo.tabType== MikeTabTopInfo.TabType.BITMAP){
            if (init){
                tabImageView.setVisibility(VISIBLE);
                tabNameView.setVisibility(GONE);
            }
            if (selected){
                tabImageView.setImageBitmap(tabInfo.selectedBitmap);
            } else {
                tabImageView.setImageBitmap(tabInfo.defaultBitmap);
            }
        }
    }

    @Override
    public void resetHeight(int height) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = height;
        setLayoutParams(layoutParams);
        getTabNameView().setVisibility(GONE);
    }

    @Override
    public void onTabSelectedChange(int index, @Nullable MikeTabTopInfo prevInfo, @NonNull MikeTabTopInfo nextInfo) {
        if (prevInfo!=tabInfo&&nextInfo!=tabInfo||prevInfo==nextInfo){
            return;
        }
        if (prevInfo==tabInfo){
            inflateInfo(false,false);
        } else {
            inflateInfo(true,false);
        }
    }

    @ColorInt
    private int getTextColor(Object color){
        if (color instanceof String){
            return Color.parseColor((String) color);
        } else {
            return (int) color;
        }
    }
}
