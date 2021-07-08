package com.mikeyyds.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mikeyyds.library.util.MikeDisplayUtil;

public abstract class MikeOverView extends FrameLayout {

    public enum MikeRefreshState {
        STATE_INIT,
        STATE_VISIBLE,
        STATE_REFRESH,
        STATE_OVER,
        STATE_OVER_RELEASE
    }

    protected MikeRefreshState mState = MikeRefreshState.STATE_INIT;
    public int mPullRefreshHeight;
    public float minDamp = 1.6f;
    public float maxDamp = 2.2f;

    public MikeOverView(@NonNull Context context) {
        super(context);
        preInit();
    }

    public MikeOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        preInit();
    }

    public MikeOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        preInit();
    }

    protected void preInit() {
        mPullRefreshHeight = MikeDisplayUtil.dp2px(66, getResources());
        init();
    }

    public abstract void init();

    protected abstract void onScroll(int scrollY, int pullRefreshHeight);

    protected abstract void onVisible();

    public abstract void onOver();

    public abstract void onRefresh();

    public abstract void onFinish();

    public void setState(MikeRefreshState state) {
        this.mState = state;
    }

    public MikeRefreshState getState() {
        return mState;
    }
}
