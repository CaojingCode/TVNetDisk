package com.android.tvnetdisk.viewmodel

import androidx.lifecycle.viewModelScope
import com.android.tvnetdisk.entity.CampusResourcesEntity
import com.android.tvnetdisk.help.MemoryCallBack
import com.blankj.utilcode.util.*
import com.danikula.videocache.HttpProxyCacheServer
import com.google.gson.reflect.TypeToken
import com.jijia.kotlinlibrary.base.AppLiveData
import com.jijia.kotlinlibrary.base.BaseViewModel
import com.jijia.kotlinlibrary.entity.ApiResponse
import com.shuyu.gsyvideoplayer.cache.ProxyCacheManager
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.request.RequestCall
import kotlinx.coroutines.launch
import okhttp3.Call


class TVFragmentViewModel : BaseViewModel() {

    var filesLiveData = AppLiveData<MutableList<CampusResourcesEntity>>()
    private var proxyCacheServer: HttpProxyCacheServer? = null
    private var requestCall: RequestCall? = null


    fun httpCampusResources(id: String) {
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


    /**
     * 下载视频缓存
     */
    fun startDownload(url: String) {
        proxyCacheServer = ProxyCacheManager.instance().newProxy(Utils.getApp())
        //拿到代理的URl
        var proxyUrl = proxyCacheServer?.getProxyUrl(url)
       if (!(proxyCacheServer?.isCached(url)!!)){
           LogUtils.d("proxyUrl=$proxyUrl")
           //下载demo然后设置
           requestCall = OkHttpUtils.get().url(proxyUrl)
               .build()
           requestCall?.execute(object : MemoryCallBack() {
               override fun onError(call: Call?, e: Exception?, id: Int) {
                   LogUtils.d("下载==缓存失败${e?.message}")

               }
               override fun inProgress(progress: Float, total: Long, id: Int) {
                   LogUtils.d("下载进度===${progress}")
               }
               override fun onResponse(response: Boolean, id: Int) {
                   stopDownload()
                   ToastUtils.showShort("下载缓存成功")
                   //保存缓存的时间，一天后删除缓存
                   SPUtils.getInstance().put("cacheTime",TimeUtils.getNowMills())
               }
           })
       }else{
           ToastUtils.showShort("缓存已经存在")
       }
    }

    private fun stopDownload() {
        if (requestCall != null) {
            requestCall?.cancel()
            requestCall = null
        }
        proxyCacheServer?.shutdown()
    }

}