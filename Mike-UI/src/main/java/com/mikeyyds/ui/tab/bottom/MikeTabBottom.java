package com.mikeyyds.ui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mikeyyds.ui.R;
import com.mikeyyds.ui.tab.common.IMikeTab;

public class MikeTabBottom extends RelativeLayout implements IMikeTab<MikeTabBottomInfo<?>> {
    private MikeTabBottomInfo<?> tabInfo;
    private ImageView tabImageView;
    private TextView tabIconView;
    private TextView tabNameView;

    public MikeTabBottom(Context context) {
        this(context,null);
    }

    public MikeTabBottom(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MikeTabBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.mike_tab_bottom,this);
        tabImageView = findViewById(R.id.iv_image);
        tabIconView = findViewById(R.id.tv_icon);
        tabNameView = findViewById(R.id.tv_name);
    }

    public MikeTabBottomInfo<?> getTabInfo() {
        return tabInfo;
    }

    public ImageView getTabImageView() {
        return tabImageView;
    }

    public TextView getTabIconView() {
        return tabIconView;
    }

    public TextView getTabNameView() {
        return tabNameView;
    }

    @Override
    public void setMikeTabInfo(@NonNull MikeTabBottomInfo data) {
        this.tabInfo = data;
        inflateInfo(false,true);
    }

    private void inflateInfo(boolean selected,boolean init) {
        if (tabInfo.tabType== MikeTabBottomInfo.TabType.ICON){
            if (init){
                tabImageView.setVisibility(GONE);
                tabIconView.setVisibility(VISIBLE);
                Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),tabInfo.iconFont);
                tabIconView.setTypeface(typeface);
                if (!TextUtils.isEmpty(tabInfo.name)){
                    tabNameView.setText(tabInfo.name);
                }
            }
            if (selected){
                tabIconView.setText(TextUtils.isEmpty(tabInfo.selectedIconName)?tabInfo.defaultIconName:tabInfo.selectedIconName);
                tabIconView.setTextColor(getTextColor(tabInfo.tintColor));
                tabNameView.setTextColor(getTextColor(tabInfo.tintColor));
            } else {
                tabIconView.setText(tabInfo.defaultIconName);
                tabIconView.setTextColor(getTextColor(tabInfo.defaultColor));
                tabNameView.setTextColor(getTextColor(tabInfo.defaultColor));
            }
        } else if (tabInfo.tabType== MikeTabBottomInfo.TabType.BITMAP){
            if (init){
                tabImageView.setVisibility(VISIBLE);
                tabIconView.setVisibility(GONE);
                if (!TextUtils.isEmpty(tabInfo.name)){
                    tabNameView.setText(tabInfo.name);
                }
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
    public void onTabSelectedChange(int index, @Nullable MikeTabBottomInfo prevInfo, @NonNull MikeTabBottomInfo nextInfo) {
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
