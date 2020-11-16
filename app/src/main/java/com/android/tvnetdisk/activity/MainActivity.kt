package com.android.tvnetdisk.activity

import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.tvnetdisk.R
import com.android.tvnetdisk.adapter.MainVPFragmentAdapter
import com.android.tvnetdisk.adapter.TabBaseAdapter
import com.android.tvnetdisk.entity.ColumnEntity
import com.android.tvnetdisk.fragment.TVBaseFragment
import com.android.tvnetdisk.viewmodel.MainViewModel
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.Utils
import com.owen.tvrecyclerview.widget.SimpleOnItemListener
import com.owen.tvrecyclerview.widget.TvRecyclerView
import com.shuyu.gsyvideoplayer.cache.ProxyCacheManager
import kotlinx.android.synthetic.main.activity_main.*

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
        SPUtils.getInstance().put("cacheTime", TimeUtils.getNowMills())
        var cacheTime = SPUtils.getInstance().getLong("cacheTime")
        LogUtils.d("cacheTime=$cacheTime")

        //如果当前时间大于缓存时间24小时，则执行清除缓存
        if (TimeUtils.getNowMills() > cacheTime+1000*60*60*24) {
            ProxyCacheManager.instance().clearCache(Utils.getApp(), null, "")
        }

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        vpFragment.offscreenPageLimit = 6
        vpFragmentAdapter = MainVPFragmentAdapter(supportFragmentManager)
        vpFragment.adapter = vpFragmentAdapter
        tabAdapter = TabBaseAdapter(this)
        tabLayout.adapter = tabAdapter
        tabLayout.setSelection(0)

        tabLayout.setOnItemListener(object : SimpleOnItemListener() {
            override fun onItemSelected(parent: TvRecyclerView?, itemView: View, position: Int) {
//                onMoveFocusBorder(itemView, 1.0f)
                for (i in columns.indices) {
                    columns[i].isSelect = position == i
                    tabAdapter.notifyItemChanged(i)
                }
                vpFragment.setCurrentItem(position, false)
            }
        })
//        tabLayout.setOnFocusChangeListener { view, hasFocus ->
//            mFocusBorder.isVisible = hasFocus
//        }

    }

    var columns = mutableListOf<ColumnEntity>()

    override fun initData() {
        mainViewModel.navigationLiveData.observe(this, Observer {
            vpFragmentAdapter.fragments = it["fragments"] as ArrayList<Fragment>
            vpFragmentAdapter.notifyDataSetChanged()

            columns = it["tabEntity"] as MutableList<ColumnEntity>
//            tabLayout.selectedPosition = 0
            tabAdapter.setDatas(columns)
        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_MENU -> {
                //菜单键

                var fragment = vpFragmentAdapter.getItem(vpFragment.currentItem) as TVBaseFragment
                if (event != null) {
                    fragment.clickMenu()
                }
            }

        }
        return super.onKeyDown(keyCode, event)
    }


}