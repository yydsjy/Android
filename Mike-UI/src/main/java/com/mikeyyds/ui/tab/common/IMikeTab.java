package com.mikeyyds.ui.tab.common;

import androidx.annotation.Px;

import androidx.annotation.NonNull;


public interface IMikeTab<D> extends IMikeTabLayout.OnTabSelectedListener<D> {
    void setMikeTabInfo(@NonNull D data);

    void resetHeight(@Px int height);
}
