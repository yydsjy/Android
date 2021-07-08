package com.mikeyyds.ui.banner.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

public class MikeViewPager extends ViewPager {
    private int mIntervalTime;

    private boolean mAutoplay = true;
    private boolean isLayout;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            next();
            mHandler.postDelayed(this,mIntervalTime);
        }
    };

    public MikeViewPager(@NonNull Context context) {
        super(context);
    }

    public void setmIntervalTime(int mIntervalTime) {
        this.mIntervalTime = mIntervalTime;
    }

    public void setScrollDuration(int duration){

        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            scrollerField.set(this,new MikeBannerScoller(getContext(),duration));
        } catch (Exception e) {

        }
    }

    public void start() {
        mHandler.removeCallbacksAndMessages(null);
        if (mAutoplay){
            mHandler.postDelayed(mRunnable,mIntervalTime);
        }
    }

    public void stop() {
        mHandler.removeCallbacksAndMessages(null);
    }

    public void setAutopaly(boolean autopaly){
        this.mAutoplay = autopaly;
        if (!autopaly){
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    start();
                    break;
            default:
                stop();
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isLayout && getAdapter()!=null&&getAdapter().getCount()>0){
            try {
                Field mScroller = ViewPager.class.getDeclaredField("mFirstLayout");
                mScroller.setAccessible(true);
                mScroller.set(this,false);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        start();

    }

    @Override
    protected void onDetachedFromWindow() {
        if (((Activity)getContext()).isFinishing()){
            super.onDetachedFromWindow();
        }
        stop();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        isLayout = true;
    }

    private int next() {
        int nextPosition = -1;
        if (getAdapter()==null||getAdapter().getCount()<=1){
            stop();
            return nextPosition;
        }

        nextPosition = getCurrentItem()+1;
        if (nextPosition>=getAdapter().getCount()){
            nextPosition = ((MikeBannerAdapter)getAdapter()).getFirstItem();
        }
        setCurrentItem(nextPosition,true);
        return nextPosition;
    }


}
