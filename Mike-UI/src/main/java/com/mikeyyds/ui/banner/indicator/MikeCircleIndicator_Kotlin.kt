package com.mikeyyds.ui.banner.indicator

import android.content.Context
import android.media.Image
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.view.forEachIndexed
import com.mikeyyds.library.util.MikeDisplayUtil
import com.mikeyyds.ui.R

class MikeCircleIndicator_Kotlin @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet?=null,
    defStyleAttr:Int=0):FrameLayout(context,attrs,defStyleAttr),MikeIndicator<FrameLayout?>{
    companion object{
        private const val VWC = ViewGroup.LayoutParams.WRAP_CONTENT
    }

    @DrawableRes
    private val mPointNormal = R.drawable.shape_point_normal

    @DrawableRes
    private val mPointSelected = R.drawable.shape_point_select

    private var mPointLeftRightPadding = 0
    private var mPointTopBottomPadding = 0

    init {
        mPointLeftRightPadding = MikeDisplayUtil.dp2px(5f,getContext().resources)
        mPointTopBottomPadding = MikeDisplayUtil.dp2px(15f,getContext().resources)
    }

    override fun get(): FrameLayout? {
        return this
    }

    override fun onInflate(count: Int) {
        removeAllViews()
        if (count<=0) return
        val groupView = LinearLayout(context)
        groupView.orientation = LinearLayout.HORIZONTAL

        var imageView: ImageView
        val imageViewParams = LinearLayout.LayoutParams(VWC, VWC)
        imageViewParams.gravity = Gravity.CENTER_VERTICAL
        imageViewParams.setMargins(
            mPointLeftRightPadding,
            mPointTopBottomPadding,
            mPointLeftRightPadding,
            mPointTopBottomPadding
        )
        for (i in 0 until count){
            imageView = ImageView(context)
            imageView.layoutParams=imageViewParams
            if (i==0){
                imageView.setImageResource(mPointSelected)
            }else{
                imageView.setImageResource(mPointNormal)
            }
            groupView.addView(imageView)
        }

        val groupViewParams = LayoutParams(VWC, VWC)
        groupViewParams.gravity = Gravity.CENTER or Gravity.BOTTOM
        addView(groupView,groupViewParams)

    }

    override fun onPointChange(current: Int, count: Int) {
        val viewGroup = getChildAt(0) as ViewGroup
        for (i in 0 until viewGroup.childCount){
            val imageView= viewGroup.getChildAt(i) as ImageView
            if (i==current){
                imageView.setImageResource(mPointSelected)
            } else {
                imageView.setImageResource(mPointNormal)
            }
            imageView.requestLayout()
        }
    }

}
