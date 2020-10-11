package com.android.tvnetdisk.activity

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.tvnetdisk.R
import com.android.tvnetdisk.adapter.MainVPFragmentAdapter
import com.android.tvnetdisk.adapter.TabBaseAdapter
import com.android.tvnetdisk.entity.TabEntity
import com.android.tvnetdisk.viewmodel.MainViewModel
import com.blankj.utilcode.util.ToastUtils
import com.owen.tvrecyclerview.widget.SimpleOnItemListener
import com.owen.tvrecyclerview.widget.TvRecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

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
        vpFragmentAdapter = MainVPFragmentAdapter(this)
        vpFragment.adapter = vpFragmentAdapter
        vpFragment.offscreenPageLimit=10
        tabAdapter= TabBaseAdapter(this)
        tabLayout.adapter = tabAdapter

        tabLayout.setOnItemListener(object: SimpleOnItemListener(){
            override fun onItemSelected(parent: TvRecyclerView?, itemView: View, position: Int) {
                onMoveFocusBorder(itemView,1.1f)
                vpFragment.currentItem=position
            }

            override fun onItemClick(parent: TvRecyclerView?, itemView: View?, position: Int) {
               ToastUtils.showLong("$position")
            }
        })
        tabLayout.setOnFocusChangeListener { view, hasFocus ->
            mFocusBorder.isVisible = hasFocus
        }
    }

    override fun initData() {
        mainViewModel.navigationLiveData.observe(this, Observer {
            vpFragmentAdapter.fragments = it["fragments"] as ArrayList<Fragment>
            tabAdapter.setDatas(it["tabEntity"] as MutableList<TabEntity>)
        })
    }

}