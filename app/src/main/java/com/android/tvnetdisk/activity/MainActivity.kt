package com.android.tvnetdisk.activity

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.tvnetdisk.R
import com.android.tvnetdisk.viewmodel.MainViewModel
import com.flyco.tablayout.listener.CustomTabEntity
import com.jijia.kotlinlibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : BaseActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun initData() {
        mainViewModel.navigationLiveData.observe(this, Observer {
            tabLayout.setTabData(
                it["tabEntity"] as ArrayList<CustomTabEntity>,
                this,
                R.id.vpFragment,
                it["fragments"] as ArrayList<Fragment>
            )
        })
    }

    override fun initView() {
        titleBarHide()
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun layoutResID(): Int {
        return R.layout.activity_main
    }

}