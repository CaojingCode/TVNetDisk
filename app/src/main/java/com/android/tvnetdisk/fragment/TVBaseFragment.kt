package com.android.tvnetdisk.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.tvnetdisk.R
import com.android.tvnetdisk.activity.MainActivity
import com.android.tvnetdisk.adapter.GridAdapter
import com.android.tvnetdisk.entity.CampusResourcesEntity
import com.android.tvnetdisk.help.getRandomColor
import com.android.tvnetdisk.viewmodel.TVFragmentViewModel
import com.blankj.utilcode.util.*
import com.jijia.kotlinlibrary.base.BaseFragment
import com.owen.focus.FocusBorder
import com.owen.tvrecyclerview.TwoWayLayoutManager
import com.owen.tvrecyclerview.widget.GridLayoutManager
import com.owen.tvrecyclerview.widget.SimpleOnItemListener
import com.owen.tvrecyclerview.widget.TvRecyclerView
import com.owen.widget.OnItemListener
import kotlinx.android.synthetic.main.fragment_index.*
import kotlinx.android.synthetic.main.fragment_index.view.*

open class TVBaseFragment : BaseFragment() {
    private var id: String = ""
    lateinit var gridAdapter: GridAdapter
    lateinit var fragmentViewModel: TVFragmentViewModel
    var list: MutableList<CampusResourcesEntity> = mutableListOf()

    companion object {
        fun newInstance(id: String): TVBaseFragment {
            var bundle = Bundle()
            bundle.putString("id", id)
            var fragment = TVBaseFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun initView(view: View) {
        id = arguments?.getString("id").toString()
        fragmentViewModel = ViewModelProvider(this).get(TVFragmentViewModel::class.java)
        fragmentViewModel.httpCampusResources(id)
        if (activity != null) gridAdapter = GridAdapter(activity!!)
        view.rvGridView.adapter = gridAdapter


        view.rvGridView.setOnItemListener(object : SimpleOnItemListener() {
            override fun onItemClick(parent: TvRecyclerView?, itemView: View?, position: Int) {
                var imageUrls = getImageUrls()
                var clickPosition = 0
                for (j in imageUrls.indices) {
                    if (list[position].fileURL == imageUrls[j]) {
                        clickPosition = j
                        break
                    }
                }
                when (gridAdapter.getItem(position).type) {
                    1 -> {
                        (activity as MainActivity).perViewData(clickPosition, imageUrls)
                    }
                    2 -> {
                        //打开文件夹
                        var fragment = newInstance(gridAdapter.getItem(position).id)
                        var transaction = activity?.supportFragmentManager?.beginTransaction()
                        transaction?.add(R.id.flFragment, fragment)?.addToBackStack(null)?.commit()
                        view.rvGridView.visibility = View.GONE

                    }
                }
            }

        })


    }

    override fun initData() {
        fragmentViewModel.filesLiveData.observe(this, Observer {
            gridAdapter.setDatas(it)
            gridAdapter.notifyDataSetChanged()
            list.clear()
            list.addAll(it)
        })
    }


    /**
     * 生成图片集合
     *
     *
     */
    fun getImageUrls(): ArrayList<String> {
        var imageUrls = ArrayList<String>()
        for (i in list.indices) {
            if (list[i].type == 1) {
                imageUrls.add(list[i].fileURL)
            }
        }
        return imageUrls
    }


    override fun layoutId(): Int {
        return R.layout.fragment_index
    }




}