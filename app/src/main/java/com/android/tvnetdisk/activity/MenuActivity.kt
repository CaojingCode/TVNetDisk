package com.android.tvnetdisk.activity

import android.view.KeyEvent
import com.android.tvnetdisk.R
import com.android.tvnetdisk.fragment.TVBaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu.*

const val ResultRefresh=111
const val ResultCache=222


class MenuActivity :TVBaseActivity(){


    override fun layoutTVResID(): Int {
        return R.layout.activity_menu
    }

    override fun initView() {
        super.initView()
        titleBarHide()
    }

    override fun initData() {
        btnRefresh.setOnClickListener {
            //刷新页面
            setResult(ResultRefresh)
            finish()
        }

        btnCache.setOnClickListener {
            //下载缓存
            setResult(ResultCache)
            finish()
        }
    }


}