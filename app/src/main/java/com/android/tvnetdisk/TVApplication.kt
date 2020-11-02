package com.android.tvnetdisk

import com.android.tvnetdisk.help.RequestInterceptor
import com.blankj.utilcode.util.LogUtils
import com.jijia.kotlinlibrary.base.BaseApplication
import com.jijia.kotlinlibrary.net.RetrofitManage

class TVApplication :BaseApplication(){

    override fun onCreate() {
        RetrofitManage.setRequestInterceptor(RequestInterceptor())
        super.onCreate()
        LogUtils.getConfig().setLogSwitch(true).setConsoleSwitch(true).globalTag = "BaseAndroid"
    }

    override fun getBaseUrl(): String {
        return "http://47.115.8.223:8080/"
    }
}