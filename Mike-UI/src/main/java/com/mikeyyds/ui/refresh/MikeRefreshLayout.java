package com.mikeyyds.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MikeRefreshLayout extends FrameLayout implements MikeRefresh {
    private MikeOverView.MikeRefreshState mState;
    private GestureDetector mGestureDetector;
    private MikeRefreshListener mMikeRefreshListener;
    protected MikeOverView mMikeOverView;
    private int mLastY;
    private boolean disableRefreshScroll;
    private AutoScroller mAutoScroller;

    public MikeRefreshLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public MikeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public MikeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        mGestureDetector = new GestureDetector(getContext(), mikeGestureDetector);
        mAutoScroller = new AutoScroller();
    }

    MikeGestureDetector mikeGestureDetector = new MikeGestureDetector() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if (Math.abs(distanceX) > Math.abs(distanceY) || (mMikeRefreshListener != null && !mMikeRefreshListener.enableRefresh())) {
                return false;
            }

            View child = MikeScrollUtil.findScrollableChild(MikeRefreshLayout.this);
            if (MikeScrollUtil.childScrolled(child)) {
                return false;
            }

            if (disableRefreshScroll && mState == MikeOverView.MikeRefreshState.STATE_REFRESH) {
                return true;
            }

            if (distanceY <= 0 && mState != MikeOverView.MikeRefreshState.STATE_REFRESH) {
                if (mState != MikeOverView.MikeRefreshState.STATE_OVER_RELEASE) {
                    int seed;
                    if (child.getTop() < mMikeOverView.mPullRefreshHeight) {
                        seed = (int) (mLastY / mMikeOverView.minDamp);
                    } else {
                        seed = (int) (mLastY / mMikeOverView.maxDamp);
                    }
                    moveDown(seed, true);
                    mLastY = (int) -distanceY;
                }
                return true;
            }

            return false;
        }
    };

    private void moveDown(int offsetY, boolean nonAuto) {
        View head = getChildAt(0);
        View child = getChildAt(1);
        int childTop = child.getTop() + offsetY;

        if (childTop == 0) {
            mState = MikeOverView.MikeRefreshState.STATE_INIT;
        } else if (childTop <= mMikeOverView.mPullRefreshHeight) {
            if (mMikeOverView.getState() != MikeOverView.MikeRefreshState.STATE_VISIBLE && nonAuto) {
                mMikeOverView.onVisible();
                mMikeOverView.setState(MikeOverView.MikeRefreshState.STATE_VISIBLE);
                mState = MikeOverView.MikeRefreshState.STATE_VISIBLE;
            }
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
            if (childTop == mMikeOverView.mPullRefreshHeight && mState == MikeOverView.MikeRefreshState.STATE_OVER_RELEASE) {
                refresh();
            }
        } else {
            if (mMikeOverView.getState() != MikeOverView.MikeRefreshState.STATE_OVER && nonAuto) {
                mMikeOverView.onOver();
                mMikeOverView.setState(MikeOverView.MikeRefreshState.STATE_OVER);
                mState = MikeOverView.MikeRefreshState.STATE_OVER;
            }
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
        }
        if (mMikeOverView != null) {
            mMikeOverView.onScroll(head.getBottom(), mMikeOverView.mPullRefreshHeight);
        }
    }

    private void refresh() {
        if (mMikeRefreshListener != null) {
            mState = MikeOverView.MikeRefreshState.STATE_REFRESH;
            mMikeOverView.onRefresh();
            mMikeOverView.setState(MikeOverView.MikeRefreshState.STATE_REFRESH);
            mMikeRefreshListener.onRefresh();
        }
    }


    @Override
    public void setDisableRefreshScroll(boolean disableRefreshScroll) {
        this.disableRefreshScroll = disableRefreshScroll;
    }

    @Override
    public void refreshFinished() {
        mMikeOverView.onFinish();
        mMikeOverView.setState(MikeOverView.MikeRefreshState.STATE_OVER_RELEASE);
        View head = getChildAt(0);
        int bottom = head.getBottom();
        if (bottom > 0) {
            recover(bottom);
        }
        mState = MikeOverView.MikeRefreshState.STATE_OVER_RELEASE;

    }

    @Override
    public void setRefreshListener(MikeRefreshListener mikeRefreshListener) {
        this.mMikeRefreshListener = mikeRefreshListener;
    }

    @Override
    public void setRefreshOverView(MikeOverView mikeOverView) {
        if (this.mMikeOverView != null) {
            removeView(mMikeOverView);
        }
        this.mMikeOverView = mikeOverView;
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mMikeOverView, 0, params);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        View head = getChildAt(0);
        View child = getChildAt(1);
        if (head != null && child != null) {
            int childTop = child.getTop();
            head.layout(0, childTop - head.getMeasuredHeight(), right, childTop);
            child.layout(0, childTop, right, childTop + child.getMeasuredHeight());
        }

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View head = getChildAt(0);
        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            if (head.getBottom() > 0 && mState!= MikeOverView.MikeRefreshState.STATE_REFRESH) {
                recover(head.getBottom());
                mLastY = 0;
                return true;
            }
        }
        boolean consumed = mGestureDetector.onTouchEvent(ev);
        if (consumed) {
            ev.setAction(MotionEvent.ACTION_CANCEL);
            return super.dispatchTouchEvent(ev);
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    private void recover(int dis) {

        if (mMikeRefreshListener != null && dis > mMikeOverView.mPullRefreshHeight) {
            mAutoScroller.recover(dis - mMikeOverView.mPullRefreshHeight);
            mState = MikeOverView.MikeRefreshState.STATE_OVER_RELEASE;

        } else {
            mAutoScroller.recover(dis);
        }
    }

    private class AutoScroller implements Runnable {
        private Scroller mScroller;
        private int mLastY;
        private boolean mIsFinished;

        public AutoScroller() {
            mScroller = new Scroller(getContext(), new LinearInterpolator());
            mIsFinished = true;
        }

        @Override
        public void run() {
            if (mScroller.computeScrollOffset()) {
                moveDown(mLastY - mScroller.getCurrY(), false);
                mLastY = mScroller.getCurrY();
                post(this);
            } else {
                removeCallbacks(this);
                mIsFinished = true;
            }
        }

        void recover(int dis) {
            if (dis <= 0) return;
            removeCallbacks(this);
            mLastY = 0;
            mIsFinished = false;
            mScroller.startScroll(0, 0, 0, dis, 300);
            post(this);
        }

        boolean isIsFinished() {
            return mIsFinished;
        }
    }

}
