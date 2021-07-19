package com.mikeyyds.library.app.demo.banner

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mikeyyds.library.app.ActivityManager
import com.mikeyyds.library.app.R
import com.mikeyyds.ui.banner.MikeBanner
import com.mikeyyds.ui.banner.core.MikeBannerMo
import com.mikeyyds.ui.banner.indicator.MikeCircleIndicator
import com.mikeyyds.ui.banner.indicator.MikeIndicator
import com.mikeyyds.ui.banner.indicator.MikeNumberIndicator


class MikeBannerDemoActivity : AppCompatActivity() {
    private var urls = arrayOf(
        "https://www.devio.org/img/beauty_camera/beauty_camera1.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera2.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera3.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera4.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera5.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera6.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera7.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera8.jpeg"
    )
    private var autoplay:Boolean = false
    private var mikeIndicator:MikeIndicator<*>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mike_banner_demo)
        mikeIndicator = MikeCircleIndicator(this)
        initView(mikeIndicator,false)
        findViewById<Switch>(R.id.auto_play).setOnCheckedChangeListener{_,isChecked->
            autoplay = isChecked
            initView(mikeIndicator,autoplay)
        }
        findViewById<View>(R.id.tv_switch).setOnClickListener{
            if (mikeIndicator is MikeCircleIndicator){
                mikeIndicator = MikeNumberIndicator(this)
            } else{
                mikeIndicator = MikeCircleIndicator(this)
            }
            initView(mikeIndicator,autoplay)
        }


    }

    private fun initView(mikeIndicator:MikeIndicator<*>?,autoplay:Boolean) {
        val mBanner = findViewById<MikeBanner>(R.id.banner)
        val moList:MutableList<MikeBannerMo> = ArrayList()
        for (i in 0..7){
            val mo = BannerMo()
            mo.url = urls[i%urls.size]
            moList.add(mo)
        }
        mBanner.setMikeIndicator(mikeIndicator)
        mBanner.setAutoplay(autoplay)
        mBanner.setIntervalTime(2000)
        mBanner.setBannerData(R.layout.banner_item_layout,moList)
        mBanner.setBindAdapter{viewHolder, mo, position ->
            val imageView:ImageView = viewHolder.findViewById(R.id.iv_image)
            Glide.with(this@MikeBannerDemoActivity).load(mo.url).into(imageView)
            val titleView:TextView = viewHolder.findViewById(R.id.tv_title)
            titleView.text = mo.url;
            Log.d("----position:",position.toString()+"url:"+mo.url)
        }
        mBanner.setScrollerDuration(2000)
    }
}