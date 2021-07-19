package com.mikeyyds.library.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mikeyyds.library.app.demo.log.MikeLogDemoActivity
import com.mikeyyds.library.app.demo.banner.MikeBannerDemoActivity
import com.mikeyyds.library.app.demo.item.MikeItemDemoActivity
import com.mikeyyds.library.app.demo.refresh.MikeRefreshDemoActivity
import com.mikeyyds.library.app.demo.tab.MikeTabBottomDemoActivity
import com.mikeyyds.library.app.demo.tab.MikeTabTopDemoActivity
import com.mikeyyds.library.app.fragment.MikeFragmentActivity
import com.mikeyyds.library.app.mike_navigation.MikeNavActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityManager.instance.addFrontbackCallback(object:ActivityManager.FrontbackCallback{
            override fun onChanged(front: Boolean) {
                Toast.makeText(applicationContext, "MainActivity front:"+front, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tv_MikeLog -> {
                startActivity(Intent(this,
                    MikeLogDemoActivity::class.java))
            }
            R.id.tv_tab_bottom -> {
                startActivity(Intent(this,MikeTabBottomDemoActivity::class.java))
            }
            R.id.tv_tab_top ->{
                startActivity(Intent(this,MikeTabTopDemoActivity::class.java))
            }
            R.id.tv_mike_refresh ->{
                startActivity(Intent(this,MikeRefreshDemoActivity::class.java))
            }
            R.id.tv_mike_banner ->{
                startActivity(Intent(this, MikeBannerDemoActivity::class.java))
            }
            R.id.tv_mike_item ->{
                startActivity(Intent(this,MikeItemDemoActivity::class.java))
            }
            R.id.tv_mike_navigation ->{
                startActivity(Intent(this, MikeNavActivity::class.java))
            }
            R.id.tv_fragment ->{
                startActivity(Intent(this, MikeFragmentActivity::class.java))
            }
            R.id.tv_test ->{
                startActivity(Intent(this, TestActivity::class.java))
            }

        }
    }
}