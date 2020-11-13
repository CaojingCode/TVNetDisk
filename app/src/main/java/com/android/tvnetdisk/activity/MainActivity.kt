package com.android.tvnetdisk.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.tvnetdisk.R
import com.android.tvnetdisk.adapter.MainVPFragmentAdapter
import com.android.tvnetdisk.adapter.TabBaseAdapter
import com.android.tvnetdisk.entity.ColumnEntity
import com.android.tvnetdisk.fragment.PreViewFragment
import com.android.tvnetdisk.viewmodel.MainViewModel
import com.owen.tvrecyclerview.widget.SimpleOnItemListener
import com.owen.tvrecyclerview.widget.TvRecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_tab_layout.view.*

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
            for (i in columns.indices) {
                if (columns[i].flag) {
                    tabLayout.selectedPosition = i
                    break
                }
            }
            tabAdapter.setDatas(columns)
        })
    }


}