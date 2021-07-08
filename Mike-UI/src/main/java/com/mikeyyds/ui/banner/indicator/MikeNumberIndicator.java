package com.mikeyyds.ui.banner.indicator;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MikeNumberIndicator extends FrameLayout implements MikeIndicator<FrameLayout> {
    private static final int VWC = WRAP_CONTENT;

    public MikeNumberIndicator(@NonNull Context context) {
        super(context);
    }

    public MikeNumberIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MikeNumberIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public FrameLayout get() {
        return this;
    }

    @Override
    public void onInflate(int count) {
        removeAllViews();
        if (count<=0) return;
        TextView textView = new TextView(getContext());
        textView.setText("1/"+count);
        textView.setTextColor(Color.WHITE);

        LayoutParams textViewLayoutParams = new LayoutParams(VWC,VWC);
        textViewLayoutParams.gravity = Gravity.END|Gravity.BOTTOM;
        textView.setLayoutParams(textViewLayoutParams);

        addView(textView,textViewLayoutParams);
    }

    @Override
    public void onPointChange(int current, int count) {
        TextView textView = (TextView) getChildAt(0);
        textView.setText(String.valueOf(current+1)+"/"+count);
        textView.requestLayout();
    }
}
