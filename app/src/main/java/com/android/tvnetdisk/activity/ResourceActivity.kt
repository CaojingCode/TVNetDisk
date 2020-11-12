package com.android.tvnetdisk.activity

import com.android.tvnetdisk.R
import com.android.tvnetdisk.fragment.TVBaseFragment

/**
 * 文件资源管理页面
 */
class ResourceActivity : TVBaseActivity() {
    override fun layoutTVResID(): Int {
        return R.layout.activity_resource
    }

    override fun initData() {
        titleBarHide()
        //接收文件夹id
        var folderId = intent.getStringExtra("folderId")
        var ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flFragment, TVBaseFragment.newInstance(folderId))
        ft.commit()
    }
}