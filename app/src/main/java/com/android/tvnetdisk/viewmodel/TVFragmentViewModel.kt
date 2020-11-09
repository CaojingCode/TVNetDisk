package com.android.tvnetdisk.viewmodel

import androidx.lifecycle.viewModelScope
import com.android.tvnetdisk.entity.CampusResourcesEntity
import com.android.tvnetdisk.entity.ColumnEntity
import com.android.tvnetdisk.entity.GridEntity
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.reflect.TypeToken
import com.jijia.kotlinlibrary.base.AppLiveData
import com.jijia.kotlinlibrary.base.BaseViewModel
import com.jijia.kotlinlibrary.entity.ApiResponse
import kotlinx.coroutines.launch

class TVFragmentViewModel : BaseViewModel() {

    var filesLiveData = AppLiveData<MutableList<CampusResourcesEntity>>()

    fun httpCampusResources(id:String){
        //要传的参数
        var hashMap = HashMap<String, String>().apply {
            put("id", id)
            put("appSecret", SPUtils.getInstance().getString("appKey"))
        }
        viewModelScope.launch {
            var bindingResponse = postData<MutableList<CampusResourcesEntity>>(
                "api/campus/getCampusResources",
                object : TypeToken<ApiResponse<MutableList<CampusResourcesEntity>>>() {}.type,
                "http://47.115.8.223:8080/", hashMap
            )
            filesLiveData.postValue(bindingResponse.data)
        }
    }

    override fun getSuccessCode(): Int {
        return 200
    }
}