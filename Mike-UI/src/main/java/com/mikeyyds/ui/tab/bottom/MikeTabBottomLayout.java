package com.mikeyyds.ui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
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

public class MikeTabBottomLayout extends FrameLayout implements IMikeTabLayout<MikeTabBottom, MikeTabBottomInfo<?>> {
    private static final String TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM";
    private List<OnTabSelectedListener<MikeTabBottomInfo<?>>> tabSelectedChangeListeners = new ArrayList<>();
    private MikeTabBottomInfo<?> selectedInfo;
    private float bottomAlpha = 1f;
    private float tabBottomHeight = 50;
    private float bottomLineHeight = 0.5f;
    private String bottomLineColor = "#dfe0e1";
    private List<MikeTabBottomInfo<?>> infoList;

    public MikeTabBottomLayout(@NonNull Context context) {
        super(context);
    }

    public MikeTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MikeTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public MikeTabBottom findTab(@NonNull MikeTabBottomInfo<?> info) {
        ViewGroup ll = findViewWithTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof MikeTabBottom){
                MikeTabBottom tab = (MikeTabBottom) child;
                if (tab.getTabInfo()==info){
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<MikeTabBottomInfo<?>> listener) {
        tabSelectedChangeListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NonNull MikeTabBottomInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    public void setTabAlpha(float alpha) {
        this.bottomAlpha = alpha;
    }

    public void setTabHeight(float tabHeight) {
        this.tabBottomHeight = tabHeight;
    }

    public void setBottomLineHeight(float bottomLineHeight) {
        this.bottomLineHeight = bottomLineHeight;
    }

    public void setBottomLineColor(String bottomLineColor) {
        this.bottomLineColor = bottomLineColor;
    }

    @Override
    public void inflateInfo(@NonNull List<MikeTabBottomInfo<?>> infoList) {
        if (infoList==null){
            return;
        }
        this.infoList = infoList;
        for (int i = getChildCount()-1;i>0;i--){
            removeViewAt(i);
        }
        selectedInfo = null;
        addBackground();

        Iterator<OnTabSelectedListener<MikeTabBottomInfo<?>>> iterator = tabSelectedChangeListeners.iterator();
        while (iterator.hasNext()){
            if (iterator.next() instanceof MikeTabBottom){
                iterator.remove();
            }
        }

        int height = MikeDisplayUtil.dp2px(tabBottomHeight,getResources());
        int width = MikeDisplayUtil.getDisplayWidthInPx(getContext())/infoList.size();
        FrameLayout fl = new FrameLayout(getContext());
        fl.setTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < infoList.size(); i++) {
            MikeTabBottomInfo<?> info = infoList.get(i);
            LayoutParams params = new LayoutParams(width,height);
            params.gravity = Gravity.BOTTOM;
            params.leftMargin=i*width;

            MikeTabBottom tabBottom = new MikeTabBottom(getContext());
            tabSelectedChangeListeners.add(tabBottom);
            tabBottom.setMikeTabInfo(info);
            fl.addView(tabBottom,params);
            tabBottom.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(info);
                }
            });
        }

        LayoutParams flParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        flParams.gravity = Gravity.BOTTOM;
        addBottomLine();
        addView(fl,flParams);
        fixContentView();
    }

    private void addBottomLine(){
        View bottomLine = new View(getContext());
        bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor));

        LayoutParams bottomLineParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,MikeDisplayUtil.dp2px(bottomLineHeight,getResources()));
        bottomLineParams.gravity = Gravity.BOTTOM;
        bottomLineParams.bottomMargin = MikeDisplayUtil.dp2px(tabBottomHeight-bottomLineHeight,getResources());
        addView(bottomLine,bottomLineParams);
        bottomLine.setAlpha(bottomAlpha);
    }

    private void onSelected(@NonNull MikeTabBottomInfo<?> nextInfo){
        for (OnTabSelectedListener<MikeTabBottomInfo<?>> listener: tabSelectedChangeListeners){
            listener.onTabSelectedChange(infoList.indexOf(nextInfo),selectedInfo,nextInfo);
        }
        this.selectedInfo = nextInfo;
    }

    private void addBackground(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.mike_bottom_layout_bg,null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MikeDisplayUtil.dp2px(tabBottomHeight,getResources()));
        params.gravity = Gravity.BOTTOM;
        addView(view,params);
        view.setAlpha(bottomAlpha);
    }

    private void fixContentView() {
        if (!(getChildAt(0) instanceof ViewGroup)) {
            return;
        }
        ViewGroup rootView = (ViewGroup) getChildAt(0);
        ViewGroup targetView = MikeViewUtil.findTypeView(rootView, RecyclerView.class);
        if (targetView==null){
            targetView = MikeViewUtil.findTypeView(rootView, ScrollView.class);
        }
        if (targetView==null){
            targetView = MikeViewUtil.findTypeView(rootView, AbsListView.class);
        }
        if (targetView!=null){
            targetView.setPadding(0,0,0,MikeDisplayUtil.dp2px(tabBottomHeight,getResources()));
            targetView.setClipToPadding(true);
        }
    }
}
