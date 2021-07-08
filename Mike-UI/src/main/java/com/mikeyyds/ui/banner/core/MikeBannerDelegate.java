package com.mikeyyds.ui.banner.core;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.mikeyyds.ui.R;
import com.mikeyyds.ui.banner.MikeBanner;
import com.mikeyyds.ui.banner.indicator.MikeCircleIndicator;
import com.mikeyyds.ui.banner.indicator.MikeCircleIndicator_Kotlin;
import com.mikeyyds.ui.banner.indicator.MikeIndicator;

import java.util.List;

public class MikeBannerDelegate implements IMikeBanner, ViewPager.OnPageChangeListener {

    private Context mContext;
    private MikeBanner mBanner;
    private MikeBannerAdapter mAdapter;
    private MikeIndicator<?> mIndicator;
    private boolean mAutoplay;
    private boolean mLoop;
    private int mIntervalTime = 5000;
    private List<? extends MikeBannerMo> mMikeBannerMos;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private IMikeBanner.OnBannerClickListener mOnBannerClickListener;
    private MikeViewPager mMikeViewPager;
    private int mScrollerDuration = -1;

    public MikeBannerDelegate(Context context, MikeBanner mikeBanner) {
        mContext = context;
        mBanner = mikeBanner;
    }

    @Override
    public void setBannerData(int layoutResId, @NonNull List<? extends MikeBannerMo> models) {
        mMikeBannerMos = models;
        init(layoutResId);
    }

    @Override
    public void setBannerData(@NonNull List<? extends MikeBannerMo> models) {
        setBannerData(R.layout.mike_banner_item_image, models);
    }

    @Override
    public void setMikeIndicator(MikeIndicator<?> mikeIndicator) {
        mIndicator = mikeIndicator;
    }

    @Override
    public void setAutoplay(boolean autoplay) {
        mAutoplay = autoplay;
        if (mAdapter != null) mAdapter.setAutoplay(autoplay);
        if (mMikeViewPager != null) mMikeViewPager.setAutopaly(autoplay);
    }

    @Override
    public void setLoop(boolean loop) {
        mLoop = loop;
    }

    @Override
    public void setIntervalTime(int intervalTime) {
        if (intervalTime > 0) mIntervalTime = intervalTime;
    }

    @Override
    public void setBindAdapter(IBindAdapter bindAdapter) {
        mAdapter.setBindAdapter(bindAdapter);
    }

    @Override
    public void setScrollerDuration(int duration) {
        mScrollerDuration = duration;
        if (mMikeViewPager != null && duration > 0) mMikeViewPager.setScrollDuration(duration);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        mOnBannerClickListener = onBannerClickListener;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null && mAdapter.getRealCount() != 0) {
            mOnPageChangeListener.onPageScrolled(position % mAdapter.getRealCount(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mAdapter.getRealCount() == 0) return;
        position = position % mAdapter.getRealCount();
        if (mOnPageChangeListener != null) mOnPageChangeListener.onPageSelected(position);
        if (mIndicator != null) mIndicator.onPointChange(position, mAdapter.getRealCount());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    private void init(int layoutResId) {
        if (mAdapter == null) {
            mAdapter = new MikeBannerAdapter(mContext);
        }

        if (mIndicator == null) {
            mIndicator = new MikeCircleIndicator_Kotlin(mContext);
        }
        mIndicator.onInflate(mMikeBannerMos.size());

        mAdapter.setLayoutResId(layoutResId);
        mAdapter.setBannerData(mMikeBannerMos);
        mAdapter.setAutoplay(mAutoplay);
        mAdapter.setLoop(mLoop);
        mAdapter.setOnBannerClickListener(mOnBannerClickListener);

        mMikeViewPager = new MikeViewPager(mContext);
        mMikeViewPager.setmIntervalTime(mIntervalTime);
        mMikeViewPager.setAutopaly(mAutoplay);
        mMikeViewPager.setAdapter(mAdapter);
        if (mScrollerDuration > 0) mMikeViewPager.setScrollDuration(mScrollerDuration);
        mMikeViewPager.addOnPageChangeListener(this);

        if ((mLoop || mAutoplay) && mAdapter.getRealCount() != 0) {
            int firstItem = mAdapter.getFirstItem();
            mMikeViewPager.setCurrentItem(firstItem, false);
        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBanner.removeAllViews();
        mBanner.addView(mMikeViewPager, layoutParams);
        mBanner.addView(mIndicator.get(), layoutParams);
    }
}
