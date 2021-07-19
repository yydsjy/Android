package com.mikeyyds.library.app.demo.log

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.mikeyyds.library.app.R
import com.mikeyyds.library.log.*

class MikeLogDemoActivity : AppCompatActivity() {
    var viewPrinter:MikeViewPrinter? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mike_log_demo)
        viewPrinter = MikeViewPrinter(this)
        findViewById<View>(R.id.btn_log).setOnClickListener{
            printLog()
        }
        viewPrinter!!.viewProvider.showFloatingView()
    }

    fun printLog(){
        MikeLogManager.getInstance().addPrinter(viewPrinter)
        MikeLog.log(object:MikeLogConfig(){
            override fun includeThread(): Boolean {
                return true
            }
            override fun stackTraceDepth(): Int {
                return 0
            }
        },MikeLogType.E,"------","5566")
        MikeLog.a("9900")
    }
}