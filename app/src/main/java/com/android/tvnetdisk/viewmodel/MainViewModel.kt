package com.android.tvnetdisk.viewmodel

import com.android.tvnetdisk.R
import com.android.tvnetdisk.fragment.IndexFragment
import com.android.tvnetdisk.fragment.OfficialAccountsFragment
import com.android.tvnetdisk.fragment.ProjectFragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.jijia.kotlinlibrary.base.AppLiveData
import com.jijia.kotlinlibrary.base.BaseViewModel
import com.jijia.kotlinlibrary.utils.addData

class MainViewModel :BaseViewModel(){


    private val tittles = arrayListOf("首页", "项目", "公众号")

    private val unSelectedIds = arrayListOf(
        R.drawable.main_index_icon,
        R.drawable.main_house_icon,
        R.drawable.main_message_icon
    )

    private val selectedIds = arrayListOf(
        R.drawable.main_index_select_icon,
        R.drawable.main_house_select_icon,
        R.drawable.main_message_select_icon
    )

    private val fragments = arrayListOf(
        IndexFragment.instant,
        ProjectFragment.instant,
        OfficialAccountsFragment.instant
    )


    //首页导航栏liveData
    val navigationLiveData = AppLiveData<HashMap<String, Any>>()

    init {
        updateNavigation()
    }

    /**
     * 更新首页底部导航栏数据
     */
    private fun updateNavigation() {
        var hashMap = HashMap<String, Any>()
        var tabEntity = mutableListOf<CustomTabEntity>()

        tabEntity.addData(tittles, unSelectedIds, selectedIds)
        hashMap["fragments"] = fragments
        hashMap["tabEntity"] = tabEntity
        navigationLiveData.postValue(hashMap)
    }
}