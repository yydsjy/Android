package com.mikeyyds.ui.banner.indicator;

import android.view.View;

public interface MikeIndicator<T extends View> {
    T get();
    void onInflate(int count);
    void onPointChange(int current, int count);
}
