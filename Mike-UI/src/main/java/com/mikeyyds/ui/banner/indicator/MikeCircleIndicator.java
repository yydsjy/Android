package com.mikeyyds.ui.banner.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mikeyyds.library.util.MikeDisplayUtil;
import com.mikeyyds.ui.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MikeCircleIndicator extends FrameLayout implements MikeIndicator<FrameLayout> {
    private static final int VWC = WRAP_CONTENT;
    @DrawableRes
    private int mPointNormal = R.drawable.shape_point_normal;
    @DrawableRes
    private int mPointSelected = R.drawable.shape_point_select;
    private int mPointLeftRightPadding;
    private int mPointTopBottomPadding;

    public MikeCircleIndicator(@NonNull Context context) {
        this(context,null);
    }

    public MikeCircleIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MikeCircleIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    @Override
    public FrameLayout get() {
        return this;
    }

    @Override
    public void onInflate(int count) {
        removeAllViews();
        if (count<=0) return;
        LinearLayout groupView = new LinearLayout(getContext());
        groupView.setOrientation(LinearLayout.HORIZONTAL);
        ImageView imageView;
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(VWC,VWC);
        imageViewParams.gravity = Gravity.CENTER_VERTICAL;
        imageViewParams.setMargins(mPointLeftRightPadding,mPointTopBottomPadding,mPointLeftRightPadding,mPointTopBottomPadding);
        for (int i = 0; i < count; i++) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(imageViewParams);
            if (i==0){
                imageView.setImageResource(mPointSelected);
            } else {
                imageView.setImageResource(mPointNormal);
            }
            groupView.addView(imageView);
        }
        LayoutParams groupViewParams = new LayoutParams(VWC,VWC);
        groupViewParams.gravity = Gravity.CENTER|Gravity.BOTTOM;
        addView(groupView,groupViewParams);
    }

    @Override
    public void onPointChange(int current, int count) {
        ViewGroup viewGroup = (ViewGroup) getChildAt(0);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            ImageView imageView = (ImageView) viewGroup.getChildAt(i);
            if (i==current) {
                imageView.setImageResource(mPointSelected);
            } else {
                imageView.setImageResource(mPointNormal);
            }
            imageView.requestLayout();
        }
    }

    private void init() {
        mPointLeftRightPadding = MikeDisplayUtil.dp2px(5,getContext().getResources());
        mPointTopBottomPadding= MikeDisplayUtil.dp2px(15,getContext().getResources());
    }
}
