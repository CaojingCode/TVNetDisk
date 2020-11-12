package com.android.tvnetdisk.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class ForbidSlideViewPage(context: Context, attrs: AttributeSet) :
    ViewPager(context, attrs) {


    override fun onTouchEvent(event: MotionEvent): Boolean {
        val handled = super.onTouchEvent(swapTouchEvent(event))
        swapTouchEvent(event)
        return handled
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val intercept = super.onInterceptTouchEvent(swapTouchEvent(event))
        swapTouchEvent(event)
        return intercept
    }


    private fun swapTouchEvent(event: MotionEvent): MotionEvent? {
        val width = width.toFloat()
        val height = height.toFloat()
        val swappedX = event.y / height * width
        val swappedY = event.x / width * height
        event.setLocation(swappedX, swappedY)
        return event
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_DPAD_RIGHT || event.keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            return false;
        }
        return super.dispatchKeyEvent(event)
    }

}