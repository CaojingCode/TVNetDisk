package com.android.tvnetdisk.activity

import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.tvnetdisk.R
import com.android.tvnetdisk.adapter.MainVPFragmentAdapter
import com.android.tvnetdisk.adapter.TabBaseAdapter
import com.android.tvnetdisk.entity.CampusResourcesEntity
import com.android.tvnetdisk.entity.ColumnEntity
import com.android.tvnetdisk.entity.TabEntity
import com.android.tvnetdisk.fragment.PreViewFragment
import com.android.tvnetdisk.viewmodel.MainViewModel
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.ToastUtils
import com.owen.tvrecyclerview.widget.SimpleOnItemListener
import com.owen.tvrecyclerview.widget.TvRecyclerView
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.text.FieldPosition
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : TVBaseActivity() {

    private lateinit var mainViewModel: MainViewModel
    lateinit var vpFragmentAdapter: MainVPFragmentAdapter
    lateinit var tabAdapter: TabBaseAdapter

    override fun layoutTVResID(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        super.initView()
        titleBarHide()
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        vpFragment.offscreenPageLimit = 6
        vpFragmentAdapter = MainVPFragmentAdapter(supportFragmentManager)
        vpFragment.adapter = vpFragmentAdapter
        tabAdapter = TabBaseAdapter(this)
        tabLayout.adapter = tabAdapter

        tabLayout.setOnItemListener(object : SimpleOnItemListener() {
            override fun onItemSelected(parent: TvRecyclerView?, itemView: View, position: Int) {
//                onMoveFocusBorder(itemView, 1.0f)
                vpFragment.setCurrentItem(position, false)
            }
        })
//        tabLayout.setOnFocusChangeListener { view, hasFocus ->
//            mFocusBorder.isVisible = hasFocus
//        }

    }

    override fun initData() {
        mainViewModel.navigationLiveData.observe(this, Observer {
            vpFragmentAdapter.fragments = it["fragments"] as ArrayList<Fragment>
            vpFragmentAdapter.notifyDataSetChanged()

            var columns = it["tabEntity"] as MutableList<ColumnEntity>
            for (i in columns.indices) {
                if (columns[i].flag) {
                    tabLayout.selectedPosition = i
                    break
                }
            }
            tabAdapter.setDatas(columns)
        })
    }

    fun perViewData(clickPosition: Int,imageUrls:ArrayList<String>) {
        var dialogFragment=PreViewFragment()
        var bundle=Bundle()
        bundle.putInt("position",clickPosition)
        bundle.putStringArrayList("imageUrls",imageUrls)
        dialogFragment.arguments=bundle
        dialogFragment.show(supportFragmentManager,"测试")
    }

//    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
//
//        return false
//    }

//    override fun onBackPressed() {
//        if (FragmentUtils.dispatchBackPress(supportFragmentManager)){
//            FragmentUtils.popAll(supportFragmentManager)
//        }else{
//            super.onBackPressed()
//        }
//    }
}