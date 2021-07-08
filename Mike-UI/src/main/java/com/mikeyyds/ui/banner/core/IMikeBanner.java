package com.mikeyyds.ui.banner.core;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.mikeyyds.ui.banner.indicator.MikeIndicator;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IMikeBanner {
    void setBannerData(@LayoutRes int layoutResId, @NonNull List<? extends MikeBannerMo> models);

    void setBannerData(@NonNull List<? extends MikeBannerMo> models);

    void setMikeIndicator(MikeIndicator<?> mikeIndicator);

    void setAutoplay(boolean autoplay);

    void setLoop(boolean loop);

    void setIntervalTime(int intervalTime);

    void setBindAdapter(IBindAdapter bindAdapter);

    void setScrollerDuration(int duration);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    void setOnBannerClickListener(OnBannerClickListener onBannerClickListener);

    interface OnBannerClickListener{
        void onBannerClick(@NotNull MikeBannerAdapter.MikeBannerViewHolder viewHolder,@NotNull MikeBannerMo bannerMo,int position);
    }
}
