package com.mikeyyds.library.app

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide

class TestActivity : AppCompatActivity() {
    var url = "https://www.devio.org/img/beauty_camera/beauty_camera1.jpg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val imageView= findViewById<ImageView>(R.id.iv_test)
        Glide.with(this@TestActivity).load(url).into(imageView)
        val topActivity: Activity?=ActivityManager.instance.topActivity
        if (topActivity!=null){
            findViewById<TextView>(R.id.tv_test).setText(topActivity.localClassName)
        }

        ActivityManager.instance.addFrontbackCallback(object:ActivityManager.FrontbackCallback{
            override fun onChanged(front: Boolean) {
                Toast.makeText(applicationContext, "TestActivity front:"+front, Toast.LENGTH_SHORT).show()
            }
        })

    }
}