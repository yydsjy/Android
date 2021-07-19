package com.mikeyyds.library.app.demo.tab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.mikeyyds.library.app.R;
import com.mikeyyds.ui.tab.common.IMikeTabLayout;
import com.mikeyyds.ui.tab.top.MikeTabTop;
import com.mikeyyds.ui.tab.top.MikeTabTopInfo;
import com.mikeyyds.ui.tab.top.MikeTabTopLayout;

import java.util.ArrayList;
import java.util.List;

public class MikeTabTopDemoActivity extends AppCompatActivity {

    String[] tabStr = new String[]{
            "Hot",
            "Clo",
            "Dig",
            "Sho",
            "Sna",
            "Ele",
            "Car",
            "Goo",
            "Fur",
            "Dec",
            "Spo"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mike_tab_top_demo);
        initTabTop();
    }

    private void initTabTop() {
        MikeTabTopLayout mikeTabTopLayout = findViewById(R.id.tab_top_layout);
        List<MikeTabTopInfo<?>> infoList = new ArrayList<>();
        int defaultColor = getResources().getColor(R.color.tabTopDefaultColor);
        int tintColor = getResources().getColor(R.color.tabTopTintColor);
        for (String s : tabStr) {
            MikeTabTopInfo<?> info = new MikeTabTopInfo<>(s, defaultColor, tintColor);
            infoList.add(info);
        }

        mikeTabTopLayout.inflateInfo(infoList);
        mikeTabTopLayout.addTabSelectedChangeListener(new IMikeTabLayout.OnTabSelectedListener<MikeTabTopInfo<?>>() {
            @Override
            public void onTabSelectedChange(int index, @Nullable MikeTabTopInfo<?> prevInfo, @NonNull MikeTabTopInfo<?> nextInfo) {
                Toast.makeText(MikeTabTopDemoActivity.this,nextInfo.name,Toast.LENGTH_SHORT).show();
            }
        });

        mikeTabTopLayout.defaultSelected(infoList.get(0));
    }
}