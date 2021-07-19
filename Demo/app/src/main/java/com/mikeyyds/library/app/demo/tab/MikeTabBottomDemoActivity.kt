package com.mikeyyds.library.app.demo.tab

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.mikeyyds.library.app.R
import com.mikeyyds.library.util.MikeDisplayUtil
import com.mikeyyds.ui.tab.bottom.MikeTabBottomInfo
import com.mikeyyds.ui.tab.bottom.MikeTabBottomLayout
import com.mikeyyds.ui.tab.common.IMikeTabLayout
import java.util.*
import kotlin.collections.ArrayList

class MikeTabBottomDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mike_tab_bottom_demo)
        initTabBottom()
    }

    private fun initTabBottom() {
        val mikeTabBottomLayout:MikeTabBottomLayout = findViewById(R.id.miketablayout)
        mikeTabBottomLayout.setTabAlpha(0.85f)
        val bottomInfoList:MutableList<MikeTabBottomInfo<*>> = ArrayList()
        val homeInfo = MikeTabBottomInfo(
            "Home",
            "font/iconfont.ttf",
            getString(R.string.if_home),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val infoFavourite = MikeTabBottomInfo(
            "Favourite",
            "font/iconfont.ttf",
            getString(R.string.if_favourite),
            null,
            "#ff656667",
            "#ffd44949"
        )
//        val infoCategory = MikeTabBottomInfo(
//            "Category",
//            "font/iconfont.ttf",
//            getString(R.string.if_category),
//            null,
//            "#ff656667",
//            "#ffd44949"
//        )
        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.fire,null)
        val infoCategory = MikeTabBottomInfo<String>(
            "Category",
            bitmap,
            bitmap
        )
        val infoRecommend = MikeTabBottomInfo(
            "Recommend",
            "font/iconfont.ttf",
            getString(R.string.if_recommend),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val infoProfile = MikeTabBottomInfo(
            "Me",
            "font/iconfont.ttf",
            getString(R.string.if_profile),
            null,
            "#ff656667",
            "#ffd44949"
        )

        Collections.addAll(bottomInfoList,homeInfo,infoFavourite,infoCategory,infoRecommend,infoProfile)
        mikeTabBottomLayout.inflateInfo(bottomInfoList)
        mikeTabBottomLayout.addTabSelectedChangeListener{_,_,nextInfo ->
            Toast.makeText(this@MikeTabBottomDemoActivity,nextInfo.name,Toast.LENGTH_SHORT).show()
        }
        mikeTabBottomLayout.defaultSelected(homeInfo)

        val tabBottom = mikeTabBottomLayout.findTab(infoCategory)
        tabBottom?.apply { resetHeight(MikeDisplayUtil.dp2px(66f,resources)) }

    }
}