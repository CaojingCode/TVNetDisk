package com.android.tvnetdisk.fragment

import android.view.View
import androidx.annotation.MainThread
import com.android.tvnetdisk.R
import com.jijia.kotlinlibrary.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IndexFragment : BaseFragment() {

    companion object {
        val instant: IndexFragment by lazy {
            IndexFragment()
        }
    }

    override fun initData() {
        GlobalScope.launch(Dispatchers.Main) {
            ioCode1()
            uiCode1()
            ioCode2()
            uiCode2()
            ioCode3()
            uiCode3()
        }
    }


    suspend fun  ioCode1(){
        withContext(Dispatchers.IO){
            println("我是IO线程1==${Thread.currentThread().name}")
        }
    }

    suspend fun ioCode2(){
        withContext(Dispatchers.IO) {
            println("我是IO线程2==${Thread.currentThread().name}")
        }
    }

    suspend fun ioCode3(){
        withContext(Dispatchers.IO) {
            println("我是IO线程3==${Thread.currentThread().name}")
        }
    }

    fun uiCode1(){
        println("我是UI线程1==${Thread.currentThread().name}")
    }

    fun uiCode2(){
        println("我是UI线程2==${Thread.currentThread().name}")
    }

    fun uiCode3(){
        println("我是UI线程3==${Thread.currentThread().name}")
    }

    override fun initView(view: View) {
    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }
}