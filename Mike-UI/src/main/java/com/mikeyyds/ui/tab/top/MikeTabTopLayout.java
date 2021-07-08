package com.mikeyyds.ui.tab.top;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mikeyyds.library.util.MikeDisplayUtil;
import com.mikeyyds.library.util.MikeViewUtil;
import com.mikeyyds.ui.R;

import com.mikeyyds.ui.tab.common.IMikeTabLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MikeTabTopLayout extends HorizontalScrollView implements IMikeTabLayout<MikeTabTop, MikeTabTopInfo<?>> {

    private List<OnTabSelectedListener<MikeTabTopInfo<?>>> tabSelectedChangeListeners = new ArrayList<>();
    private MikeTabTopInfo<?> selectedInfo;
    private List<MikeTabTopInfo<?>> infoList;

    public MikeTabTopLayout(@NonNull Context context) {
        this(context, null);
    }

    public MikeTabTopLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MikeTabTopLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(false);
    }

    @Override
    public MikeTabTop findTab(@NonNull MikeTabTopInfo<?> info) {
        ViewGroup ll = getRootLayout(false);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof MikeTabTop) {
                MikeTabTop tab = (MikeTabTop) child;
                if (tab.getTabInfo() == info) {
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<MikeTabTopInfo<?>> listener) {
        tabSelectedChangeListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NonNull MikeTabTopInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }


    @Override
    public void inflateInfo(@NonNull List<MikeTabTopInfo<?>> infoList) {
        if (infoList == null) {
            return;
        }
        this.infoList = infoList;
        LinearLayout linearLayout = getRootLayout(true);
        selectedInfo = null;

        Iterator<OnTabSelectedListener<MikeTabTopInfo<?>>> iterator = tabSelectedChangeListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof MikeTabTop) {
                iterator.remove();
            }
        }

        for (int i = 0; i < infoList.size(); i++) {
            MikeTabTopInfo<?> info = infoList.get(i);
            MikeTabTop tabTop = new MikeTabTop(getContext());
            tabSelectedChangeListeners.add(tabTop);
            tabTop.setMikeTabInfo(info);

            linearLayout.addView(tabTop);
            tabTop.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(info);
                }
            });
        }


    }

    private LinearLayout getRootLayout(boolean clear) {
        LinearLayout rootView = (LinearLayout) getChildAt(0);
        if (rootView == null) {
            rootView = new LinearLayout(getContext());
            rootView.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            addView(rootView, params);
        } else if (clear) {
            rootView.removeAllViews();
        }
        return rootView;
    }


    private void onSelected(@NonNull MikeTabTopInfo<?> nextInfo) {
        for (OnTabSelectedListener<MikeTabTopInfo<?>> listener : tabSelectedChangeListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
        autoScroll(nextInfo);
    }

    int tabWidth;

    private void autoScroll(MikeTabTopInfo<?> nextInfo) {
        MikeTabTop tabTop = findTab(nextInfo);
        if (tabTop == null) return;
        int index = infoList.indexOf(nextInfo);
        int[] loc = new int[2];
        int scrollWidth;
        tabTop.getLocationInWindow(loc);
        if (tabWidth == 0) {
            tabWidth = tabTop.getWidth();
        }

        if ((loc[0] + tabWidth / 2) > MikeDisplayUtil.getDisplayWidthInPx(getContext()) / 2) {
            scrollWidth = rangeScrollWidth(index, 2);
        } else {
            scrollWidth = rangeScrollWidth(index, -2);
        }

        scrollTo(getScrollX() + scrollWidth, 0);

    }

    private int rangeScrollWidth(int index, int range) {
        int scrollWidth = 0;
        for (int i = 0; i <= Math.abs(range); i++) {
            int next;
            if (range < 0) {
                next = range + i + index;
            } else {
                next = range - i + index;
            }
            if (next >= 0 && next < infoList.size()) {
                if (range < 0) {
                    scrollWidth -= scrollWidth(next, false);
                } else {
                    scrollWidth += scrollWidth(next, true);
                }
            }
        }
        return scrollWidth;
    }

    private int scrollWidth(int index, boolean toRight) {
        MikeTabTop target = findTab(infoList.get(index));
        if (target == null) return 0;
        Rect rect = new Rect();
        target.getLocalVisibleRect(rect);
        if (toRight) {
            if (rect.right > tabWidth) {
                return tabWidth;
            } else {
                return tabWidth - rect.right;
            }
        } else {
            if (rect.left <= -tabWidth) {
                return tabWidth;
            } else if (rect.left > 0) {
                return rect.left;
            }
            return 0;
        }
    }

}
