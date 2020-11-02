package com.android.tvnetdisk.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.tvnetdisk.R
import com.android.tvnetdisk.adapter.GridAdapter
import com.android.tvnetdisk.help.getRandomColor
import com.android.tvnetdisk.viewmodel.TVFragmentViewModel
import com.blankj.utilcode.util.ToastUtils
import com.jijia.kotlinlibrary.base.BaseFragment
import com.owen.focus.FocusBorder
import com.owen.tvrecyclerview.widget.SimpleOnItemListener
import com.owen.tvrecyclerview.widget.TvRecyclerView
import kotlinx.android.synthetic.main.fragment_index.view.*

open class TVBaseFragment : BaseFragment() {
    private var type: Int = 0
    lateinit var gridAdapter: GridAdapter
    lateinit var fragmentViewModel: TVFragmentViewModel

    lateinit var mFocusBorder: FocusBorder

    companion object {
        fun newInstance(type: Int): TVBaseFragment {
            var bundle = Bundle()
            bundle.putInt("type", type)
            var fragment = TVBaseFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView(view: View) {
        type = arguments?.getInt("type") ?: 0
        fragmentViewModel = ViewModelProvider(this).get(TVFragmentViewModel::class.java)
        fragmentViewModel.setType(type)
        mFocusBorder = FocusBorder.Builder()
            .asColor()
            .borderColorRes(android.R.color.holo_blue_dark)
            .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 3.2f)
            .shadowColorRes(android.R.color.holo_blue_light)
            .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 10f)
            .build(this)

//        gridAdapter = GridAdapter(activity)
//        view.rvGridView.adapter = gridAdapter
//        view.rvGridView.setBackgroundColor(getRandomColor())
//        view.rvGridView.setOnItemListener(object : SimpleOnItemListener() {
//            override fun onItemSelected(parent: TvRecyclerView?, itemView: View, position: Int) {
//                onMoveFocusBorder(itemView, 1.2f,20f)
//            }
//
//            override fun onItemClick(parent: TvRecyclerView?, itemView: View?, position: Int) {
//            }
//        })
//        view.rvGridView.setOnFocusChangeListener { view, hasFocus ->
//            mFocusBorder.isVisible = hasFocus
//        }
    }

    override fun initData() {
        fragmentViewModel.filesLiveData.observe(this, Observer {
//            gridAdapter.setDatas(it)
//            gridAdapter.notifyDataSetChanged()
        })
    }

    fun onMoveFocusBorder(focusedView: View, scale: Float) {
        mFocusBorder.onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale))
    }

    fun onMoveFocusBorder(focusedView: View, scale: Float, roundRadius: Float) {
        mFocusBorder.onFocus(
            focusedView,
            FocusBorder.OptionsFactory.get(scale, scale, roundRadius)
        )
    }

    override fun layoutId(): Int {
        return R.layout.fragment_index
    }
}