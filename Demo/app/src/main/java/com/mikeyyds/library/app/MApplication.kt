package com.mikeyyds.library.app

import android.app.Application
import com.google.gson.Gson
import com.mikeyyds.library.log.MikeConsolePrinter
import com.mikeyyds.library.log.MikeLogConfig
import com.mikeyyds.library.log.MikeLogManager

class MApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        MikeLogManager.init(object :MikeLogConfig(){
            override fun injectParser(): JsonParser {
                return JsonParser { src -> Gson().toJson(src) }
            }
            override fun getGlobalTag(): String {
                return "MApplication"
            }

            override fun enable(): Boolean {
                return true
            }
        },MikeConsolePrinter())

        ActivityManager.instance.init(this)
    }
}