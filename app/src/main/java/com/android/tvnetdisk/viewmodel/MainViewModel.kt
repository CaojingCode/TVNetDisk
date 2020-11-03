package com.android.tvnetdisk.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.android.tvnetdisk.entity.ColumnEntity
import com.android.tvnetdisk.entity.TabEntity
import com.android.tvnetdisk.fragment.TVBaseFragment
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.reflect.TypeToken
import com.jijia.kotlinlibrary.base.AppLiveData
import com.jijia.kotlinlibrary.base.BaseViewModel
import com.jijia.kotlinlibrary.entity.ApiResponse
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {


    private val tittles = arrayListOf("课程1", "课程2", "课程3", "课程4", "课程5", "课程6")


    private val fragments = arrayListOf<Fragment>()


    //首页导航栏liveData
    val navigationLiveData = AppLiveData<HashMap<String, Any>>()

    init {
        httpColumn()
    }


    private fun getFragments() {
        for (i in tittles.indices) {
            fragments.add(TVBaseFragment.newInstance(i))
        }
    }

    /**
     * 获取首页tab实体类，本地数据
     */
    private fun getTabEntity(): MutableList<TabEntity> {
        var tabList = mutableListOf<TabEntity>()
        for (i in tittles.indices) {
            var tabEntity = TabEntity(tittles[i])
            tabList.add(tabEntity)
        }
        return tabList
    }

    private fun httpColumn(){
        //要传的参数
        var hashMap = HashMap<String, String>().apply {
            put("appSecret", SPUtils.getInstance().getString("appKey"))
        }
        viewModelScope.launch {
            var bindingResponse = postData<List<ColumnEntity>>(
                "api/campus/getColumn",
                object : TypeToken<ApiResponse<List<ColumnEntity>>>() {}.type,
                "http://47.115.8.223:8080/", hashMap
            )
            bindingResponse.data?.let { updateNavigation(it) }
            ToastUtils.showShort(bindingResponse.msg)
        }
    }

    /**
     * 更新首页左边导航栏数据
     */
    private fun updateNavigation(columns:List<ColumnEntity>) {
        getFragments()
        var hashMap = HashMap<String, Any>()
        hashMap["fragments"] = fragments
        hashMap["tabEntity"] = columns
        navigationLiveData.postValue(hashMap)
    }

    override fun getSuccessCode(): Int {
        return 200
    }
}