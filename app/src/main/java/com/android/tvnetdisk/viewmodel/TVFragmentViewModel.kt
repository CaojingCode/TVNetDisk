package com.android.tvnetdisk.viewmodel

import com.android.tvnetdisk.entity.GridEntity
import com.jijia.kotlinlibrary.base.AppLiveData
import com.jijia.kotlinlibrary.base.BaseViewModel

class TVFragmentViewModel : BaseViewModel() {

    private val fileNames =
        arrayListOf("图片1.png", "图片2.png", "视频1.mp4", "视频2.mp4", "图片3.png", "图片4.jpg")

    var filesLiveData = AppLiveData<MutableList<GridEntity>>()

    var gridLists: MutableList<GridEntity> = mutableListOf()

    init {
        gridLists = getGridList()
    }

    /**
     * 获取文件数据集合
     */
    private fun getGridList(): MutableList<GridEntity> {
        var gridList = mutableListOf<GridEntity>()
        for (i in fileNames.indices) {
            var gridEntity = GridEntity(fileNames[i], "")
            gridList.add(gridEntity)
        }
        return gridList
    }

    /**
     * 根据导航类型，
     */
    fun setType(type: Int){
        var gridList = mutableListOf<GridEntity>()
        for (i in gridLists.indices) {
            var gridEntity = GridEntity("课程${type+1}"+fileNames[i], "")
            gridList.add(gridEntity)
        }
        filesLiveData.postValue(gridList)
    }

    override fun getSuccessCode(): Int {
        return 200
    }
}