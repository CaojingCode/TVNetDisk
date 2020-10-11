package com.android.tvnetdisk.viewmodel

import androidx.fragment.app.Fragment
import com.android.tvnetdisk.entity.TabEntity
import com.android.tvnetdisk.fragment.TVBaseFragment
import com.jijia.kotlinlibrary.base.AppLiveData
import com.jijia.kotlinlibrary.base.BaseViewModel

class MainViewModel : BaseViewModel() {


    private val tittles = arrayListOf("课程1", "课程2", "课程3", "课程4", "课程5", "课程6")


    private val fragments = arrayListOf<Fragment>()


    //首页导航栏liveData
    val navigationLiveData = AppLiveData<HashMap<String, Any>>()

    init {
        getFragments()
        updateNavigation()
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


    /**
     * 更新首页左边导航栏数据
     */
    private fun updateNavigation() {
        var hashMap = HashMap<String, Any>()
        hashMap["fragments"] = fragments
        hashMap["tabEntity"] = getTabEntity()
        navigationLiveData.postValue(hashMap)
    }
}