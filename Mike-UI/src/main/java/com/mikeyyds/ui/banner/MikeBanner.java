package com.mikeyyds.ui.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.mikeyyds.ui.R;
import com.mikeyyds.ui.banner.core.IBindAdapter;
import com.mikeyyds.ui.banner.core.IMikeBanner;
import com.mikeyyds.ui.banner.core.MikeBannerDelegate;
import com.mikeyyds.ui.banner.core.MikeBannerMo;
import com.mikeyyds.ui.banner.indicator.MikeIndicator;

import java.util.List;

public class MikeBanner extends FrameLayout implements IMikeBanner {



    private MikeBannerDelegate delegate;

    public MikeBanner(@NonNull Context context) {
        this(context,null);
    }

    public MikeBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MikeBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        delegate = new MikeBannerDelegate(context,this);
        initCustomAttrs(context,attrs);
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MikeBanner);
        boolean autoplay = typedArray.getBoolean(R.styleable.MikeBanner_autoplay,true);
        boolean loop = typedArray.getBoolean(R.styleable.MikeBanner_loop,true);
        int intervalTime = typedArray.getInteger(R.styleable.MikeBanner_intervalTime,-1);
        setAutoplay(autoplay);
        setLoop(loop);
        setIntervalTime(intervalTime);
        typedArray.recycle();

    }

    @Override
    public void setBannerData(int layoutResId, @NonNull List<? extends MikeBannerMo> models) {
        delegate.setBannerData(layoutResId,models);
    }

    @Override
    public void setBannerData(@NonNull List<? extends MikeBannerMo> models) {
        delegate.setBannerData(models);
    }

    @Override
    public void setMikeIndicator(MikeIndicator<?> mikeIndicator) {
        delegate.setMikeIndicator(mikeIndicator);
    }

    @Override
    public void setAutoplay(boolean autoplay) {
        delegate.setAutoplay(autoplay);
    }

    @Override
    public void setLoop(boolean loop) {
        delegate.setLoop(loop);
    }

    @Override
    public void setIntervalTime(int intervalTime) {
        delegate.setIntervalTime(intervalTime);
    }

    @Override
    public void setBindAdapter(IBindAdapter bindAdapter) {
        delegate.setBindAdapter(bindAdapter);
    }

    @Override
    public void setScrollerDuration(int duration) {
        delegate.setScrollerDuration(duration);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        delegate.setOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        delegate.setOnBannerClickListener(onBannerClickListener);
    }
}
