package com.android.tvnetdisk.fragment

import android.view.View
import com.android.tvnetdisk.R
import com.jijia.kotlinlibrary.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OfficialAccountsFragment : BaseFragment() {

    companion object {
        val instant: OfficialAccountsFragment by lazy {
            OfficialAccountsFragment()
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