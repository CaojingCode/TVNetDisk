package com.android.tvnetdisk.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.owen.widget.TvViewPager

class ForbidSlideViewPage(context: Context, attrs: AttributeSet) :
    ViewPager(context, attrs) {


    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }


}