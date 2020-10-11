package com.android.tvnetdisk.activity

import android.R
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jijia.kotlinlibrary.base.BaseActivity
import com.owen.focus.FocusBorder


abstract class TVBaseActivity : BaseActivity() {
    lateinit var mFocusBorder: FocusBorder

    override fun initData() {
    }

    override fun initView() {
        mFocusBorder = FocusBorder.Builder()
            .asColor()
            .borderColorRes(R.color.holo_blue_dark)
            .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 3.2f)
            .shadowColorRes(R.color.holo_blue_light)
            .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 10f)
            .build(this)
    }

    fun onMoveFocusBorder(focusedView: View, scale: Float) {
        mFocusBorder.onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale))
    }

    fun onMoveFocusBorder(focusedView: View, scale: Float, roundRadius: Float) {
        mFocusBorder.onFocus(
            focusedView,
            FocusBorder.OptionsFactory.get(scale, scale, roundRadius)
        )
    }

    fun setScrollListener(recyclerView:RecyclerView){

    }

    override fun layoutResID(): Int {
        return layoutTVResID()
    }

    abstract fun layoutTVResID(): Int

}