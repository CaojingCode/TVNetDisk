package com.android.tvnetdisk.fragment

import android.view.View
import com.android.tvnetdisk.R
import com.jijia.kotlinlibrary.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

class ProjectFragment : BaseFragment() {

    companion object {
        val instant: ProjectFragment by lazy {
            ProjectFragment()
        }
    }


    override fun initData() {

    }


    override fun initView(view: View) {
    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }
}