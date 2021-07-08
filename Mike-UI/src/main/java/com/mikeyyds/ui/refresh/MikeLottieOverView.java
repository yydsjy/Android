package com.mikeyyds.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.airbnb.lottie.LottieAnimationView;
import com.mikeyyds.ui.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MikeLottieOverView extends MikeOverView{
    private LottieAnimationView lottieView;

    public MikeLottieOverView(@NonNull Context context) {
        super(context);
    }

    public MikeLottieOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MikeLottieOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.mike_lottie_overview,this,true);
        lottieView = findViewById(R.id.lottie_animation);
        lottieView.setAnimation("loading_wave.json");
    }

    @Override
    protected void onScroll(int scrollY, int pullRefreshHeight) {

    }

    @Override
    protected void onVisible() {

    }

    @Override
    public void onOver() {

    }

    @Override
    public void onRefresh() {
        lottieView.setSpeed(2);
        lottieView.playAnimation();
    }

    @Override
    public void onFinish() {
        lottieView.setProgress(0f);
        lottieView.cancelAnimation();
    }
}
