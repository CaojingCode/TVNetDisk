package com.android.tvnetdisk.viewmodel

import android.provider.Settings
import androidx.lifecycle.viewModelScope
import com.android.tvnetdisk.TVApplication
import com.blankj.utilcode.util.*
import com.google.gson.reflect.TypeToken
import com.jijia.kotlinlibrary.base.AppLiveData
import com.jijia.kotlinlibrary.base.BaseApplication
import com.jijia.kotlinlibrary.base.BaseViewModel
import com.jijia.kotlinlibrary.entity.ApiResponse
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {
    override fun getSuccessCode(): Int {
        return 200
    }

    val bindingLiveData = AppLiveData<ApiResponse<String>>()

    /**
     * 获取Token
     */
    fun httpGetToken(appSecret: String) {
        //要传的参数
        var hashMap = HashMap<String, String>().apply {
            put("appID", "wuyouapp")
            put("appSecret", "HBu4h874C1ksAQ6s")
        }
        viewModelScope.launch {
            var tokenResponse = postData<String>(
                "campus/getToken",
                object : TypeToken<ApiResponse<String>>() {}.type,
                "http://47.115.8.223:8080/", hashMap
            )
            var token = tokenResponse.token
            if (token.isNotEmpty())
                SPUtils.getInstance().put("token", token)
            ToastUtils.showShort(tokenResponse.token)
            httpBinding(appSecret)
        }
    }

    /**
     * 绑定设备
     */
    private fun httpBinding(appSecret: String) {

        var androidId = Settings.System.getString(
            Utils.getApp().contentResolver,
            Settings.System.ANDROID_ID
        );

        //要传的参数
        var hashMap = HashMap<String, String>().apply {
            put("appSecret", appSecret)
            put("bindinCode", androidId)
        }
        viewModelScope.launch {
            var bindingResponse = postData<String>(
                "campus/binding",
                object : TypeToken<ApiResponse<String>>() {}.type,
                "http://47.115.8.223:8080/", hashMap
            )
            ToastUtils.showShort(bindingResponse.msg)
            bindingLiveData.postValue(bindingResponse)
        }
    }
}