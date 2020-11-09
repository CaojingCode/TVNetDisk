package com.android.tvnetdisk.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.viewpager.widget.PagerAdapter
import com.android.tvnetdisk.R
import com.android.tvnetdisk.adapter.PreViewAdapter
import com.android.tvnetdisk.entity.CampusResourcesEntity
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_preview.view.*
import kotlinx.android.synthetic.main.item_preview.view.*


class PreViewFragment : DialogFragment() {

    lateinit var adapter: PreViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_preview, container, false)
        var position: Int? = arguments?.getInt("position")
        var imageUrls=arguments?.getStringArrayList("imageUrls")
        view.vpPreView.adapter = object : PagerAdapter() {
            override fun getCount(): Int {
                return imageUrls?.size ?: 0
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                var view = LayoutInflater.from(activity).inflate(R.layout.item_preview, null)
                Glide.with(activity!!).load(imageUrls?.get(position)).into(view.ivPreView)
                container.addView(view)
                return view
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View?)
            }

        }

        if (position != null) {
            view.vpPreView.currentItem = position
        }
//        var snapHelper = PagerSnapHelper()
//        snapHelper.attachToRecyclerView(view.rvPreView)
//        adapter = PreViewAdapter(activity!!)
//        view.rvPreView.adapter = adapter
//        if (position != null) {
//            updateImage(position)
//        }
        return view
    }

}