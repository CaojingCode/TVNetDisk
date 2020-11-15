package com.android.tvnetdisk.fragment

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.tvnetdisk.R
import com.android.tvnetdisk.activity.*
import com.android.tvnetdisk.adapter.GridAdapter
import com.android.tvnetdisk.entity.CampusResourcesEntity
import com.android.tvnetdisk.viewmodel.TVFragmentViewModel
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jijia.kotlinlibrary.base.BaseFragment
import com.owen.tvrecyclerview.widget.SimpleOnItemListener
import com.owen.tvrecyclerview.widget.TvRecyclerView
import kotlinx.android.synthetic.main.fragment_index.view.*

 class TVBaseFragment : BaseFragment() {
    private var id: String = ""
    lateinit var gridAdapter: GridAdapter
    lateinit var fragmentViewModel: TVFragmentViewModel
    var list: MutableList<CampusResourcesEntity> = mutableListOf()


    companion object {
        fun newInstance(id: String): TVBaseFragment {
            var bundle = Bundle()
            bundle.putString("folderId", id)
            var fragment = TVBaseFragment()
            fragment.arguments = bundle
            return fragment
        }
    }



    override fun initView(view: View) {
        id = arguments?.getString("folderId").toString()
        fragmentViewModel = ViewModelProvider(this).get(TVFragmentViewModel::class.java)
        fragmentViewModel.httpCampusResources(id)
        if (activity != null) gridAdapter = GridAdapter(activity!!)
        view.rvGridView.adapter = gridAdapter



        view.rvGridView.setOnItemListener(object : SimpleOnItemListener() {
            override fun onItemSelected(parent: TvRecyclerView?, itemView: View?, position: Int) {

            }

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
                        if (gridAdapter.getItem(position).resourcesType == 2) {
                            //视频
                            var intent = Intent(activity, PlayVideoActivity::class.java)
                            intent.putExtra(VIDEOURL, gridAdapter.getItem(position).fileURL)
                            startActivity(intent)
                        } else {
                            //图片
                            perViewData(clickPosition, imageUrls)
                        }
                    }
                    2 -> {
                        //打开文件夹,跳转到新页面
                        var intent = Intent(activity, ResourceActivity::class.java)
                        intent.putExtra("folderId", gridAdapter.getItem(position).id)
                        startActivity(intent)
                    }
                }
            }

        })

    }

     /**
      * 缓存当前页面所有文件
      */
     private fun dowLoadCache(){
         ToastUtils.showShort("开始在后台缓存资源")
         for (i in list.indices) {
             if (list[i].type == 1 && list[i].resourcesType == 2) {
                 //视频
                 fragmentViewModel.startDownload(list[i].fileURL)
             }
             if (list[i].type == 1 && list[i].resourcesType == 1) {
                 Glide.with(this).load(list[i].fileURL).diskCacheStrategy(DiskCacheStrategy.DATA)
                     .listener(object : RequestListener<Drawable> {
                         override fun onLoadFailed(
                             e: GlideException?,
                             model: Any?,
                             target: Target<Drawable>?,
                             isFirstResource: Boolean
                         ): Boolean {
                             return false
                         }

                         override fun onResourceReady(
                             resource: Drawable?,
                             model: Any?,
                             target: Target<Drawable>?,
                             dataSource: DataSource?,
                             isFirstResource: Boolean
                         ): Boolean {
                             ToastUtils.showShort("图片缓存成功")
                             return true
                         }

                     }).preload()
             }
         }
     }

    /**
     *  显示预览图片弹框
     */
    fun perViewData(clickPosition: Int, imageUrls: ArrayList<String>) {
        var dialogFragment = PreViewFragment()
        var bundle = Bundle()
        bundle.putInt("position", clickPosition)
        bundle.putStringArrayList("imageUrls", imageUrls)
        dialogFragment.arguments = bundle
        activity?.supportFragmentManager?.let { dialogFragment.show(it, "测试") }
    }

    override fun initData() {
        fragmentViewModel.filesLiveData.observe(this, Observer {
            if (it.isNullOrEmpty())
                return@Observer
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
            if (list[i].type == 1 && list[i].resourcesType == 1) {
                imageUrls.add(list[i].fileURL)
            }
        }
        return imageUrls
    }


    override fun layoutId(): Int {
        return R.layout.fragment_index
    }


     fun clickMenu() {
         startActivityForResult(Intent(activity,MenuActivity::class.java),100)
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         if (requestCode==100){
             if (resultCode== ResultCache){
                 //下载缓存
                 dowLoadCache()
             }else{
                 //刷新页面
             }
         }
     }


}