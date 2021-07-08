package com.mikeyyds.ui.banner.core;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class MikeBannerAdapter extends PagerAdapter {
    private Context mContext;
    private SparseArray<MikeBannerViewHolder> mCachedViews = new SparseArray<>();
    private IMikeBanner.OnBannerClickListener mOnBannerClickListener;
    private IBindAdapter mBindAdapter;
    private List<? extends MikeBannerMo> models;
    private boolean mAutoplay = true;
    private boolean mLoop = false;
    private int mLayoutResId = -1;

    public MikeBannerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBindAdapter(IBindAdapter bindAdapter) {this.mBindAdapter = bindAdapter;}

    public void setOnBannerClickListener(IMikeBanner.OnBannerClickListener OnBannerClickListener){
        this.mOnBannerClickListener = OnBannerClickListener;
    }

    public void setLayoutResId(@LayoutRes int layoutResId) {this.mLayoutResId = layoutResId;}

    public void setAutoplay(boolean autoplay){this.mAutoplay=autoplay;}

    public void setLoop(boolean loop){this.mLoop=loop;}

    @Override
    public int getCount() {
        return mAutoplay?Integer.MAX_VALUE:(mLoop?Integer.MAX_VALUE:getRealCount());
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position;
        if (getRealCount()>0){
            realPosition = position%getRealCount();
        }
        MikeBannerViewHolder viewHolder = mCachedViews.get(realPosition);
        if (container.equals(viewHolder.rootView.getParent())){
            container.removeView(viewHolder.rootView);
        }

        onBind(viewHolder,models.get(realPosition),realPosition);
        // TODO: 2021-06-22  
        if (viewHolder.rootView.getParent()!=null){
            ((ViewGroup)viewHolder.rootView.getParent()).removeView(viewHolder.rootView);
        }
        container.addView(viewHolder.rootView);
        return viewHolder.rootView;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }

    public static class MikeBannerViewHolder{
        private SparseArray<View> viewSparseArray;
        View rootView;

        public MikeBannerViewHolder(View rootView) {
            this.rootView = rootView;
        }

        public View getRootView(){
            return rootView;
        }

        public <V extends View> V findViewById(int id){
            if (!(rootView instanceof ViewGroup)){
                return (V) rootView;
            }
            if (this.viewSparseArray==null){
                this.viewSparseArray=new SparseArray<>(1);
            }
            V childView = (V) viewSparseArray.get(id);
            if (childView == null) {

                childView = rootView.findViewById(id);
                this.viewSparseArray.put(id,childView);
            }
            return childView;
        }
    }

    public int getRealCount(){
        return models == null?0:models.size();
    }

    public int getFirstItem(){
        return Integer.MAX_VALUE/2-(Integer.MAX_VALUE/2)%getRealCount();
    }

    public void setBannerData(@NonNull List<? extends MikeBannerMo> models){
        this.models = models;
        initCachedView();
        notifyDataSetChanged();
    }

    protected void onBind(@NonNull final MikeBannerViewHolder viewHolder,@NonNull final MikeBannerMo bannerMo,final int position){
        viewHolder.rootView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mOnBannerClickListener!=null){
                    mOnBannerClickListener.onBannerClick(viewHolder,bannerMo,position);
                }
            }
        });

        if (mBindAdapter!=null){
            mBindAdapter.onBind(viewHolder,bannerMo,position);
        }
    }

    private View createView(LayoutInflater layoutInflater, ViewGroup parent){
        if (mLayoutResId==-1){
            throw new IllegalArgumentException("setLayoutResId is not used");
        }
        return layoutInflater.inflate(mLayoutResId,parent,false);
    }

    private void initCachedView(){
        mCachedViews = new SparseArray<>();
        for (int i = 0; i < models.size(); i++) {
            MikeBannerViewHolder viewHolder = new MikeBannerViewHolder(createView(LayoutInflater.from(mContext),null));
            mCachedViews.put(i,viewHolder);
        }

    }
}
