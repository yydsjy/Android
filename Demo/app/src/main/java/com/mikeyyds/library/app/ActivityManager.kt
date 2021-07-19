package com.mikeyyds.library.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference

class ActivityManager private constructor(){
    private val activityRefs = ArrayList<WeakReference<Activity>>()
    private val frontbackCallback = ArrayList<FrontbackCallback>()
    private var activityStartCount = 0
    private var front = true
    val topActivity:Activity?
        get(){
            if (activityRefs.size<=0) return null
            else return activityRefs[activityRefs.size-1].get()
            return null
        }

    interface FrontbackCallback{
        fun onChanged(front:Boolean)
    }

    companion object{
        val instance:ActivityManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
            ActivityManager()
        }
    }

    fun init(application: Application){
        application.registerActivityLifecycleCallbacks(InnerActivityLifecycleCallbacks())
    }

    inner class InnerActivityLifecycleCallbacks:Application.ActivityLifecycleCallbacks{
        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStarted(activity: Activity) {
            activityStartCount++
            if (!front&&activityStartCount>0){
                front = true
                onFrontBackChanged(front)
            }

        }

        override fun onActivityDestroyed(activity: Activity) {
            for (activityRef in activityRefs){
                if (activityRef!=null&&activityRef.get()==activity){
                    activityRefs.remove(activityRef)
                    break
                }
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityStopped(activity: Activity) {
            activityStartCount--
            if (activityStartCount<=0&&front){
                front = false
                onFrontBackChanged(front)
            }
        }


        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activityRefs.add(WeakReference(activity))
        }

        override fun onActivityResumed(activity: Activity) {
        }

    }

    fun addFrontbackCallback(callback: FrontbackCallback){
        frontbackCallback.add(callback)
    }

    fun removeFrontbackCallback(callback: FrontbackCallback){
        frontbackCallback.remove(callback)
    }

    private fun onFrontBackChanged(front: Boolean) {
        for (callback in frontbackCallback){
            callback.onChanged(front)
        }
    }
}
