package com.android.tvnetdisk.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.android.tvnetdisk.R
import com.android.tvnetdisk.entity.CampusResourcesEntity
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.owen.adapter.CommonBaseAdapter
import com.owen.adapter.CommonRecyclerViewAdapter
import com.owen.adapter.CommonRecyclerViewHolder
import com.owen.adapter.CommonViewHolder

class GridAdapter(context: Context) : CommonRecyclerViewAdapter<CampusResourcesEntity>(context) {

    override fun getItemLayoutId(viewType: Int): Int {
        return R.layout.item_grid_layout
    }

    override fun onBindItemHolder(
        helper: CommonRecyclerViewHolder,
        item: CampusResourcesEntity?,
        position: Int
    ) {

        val imageView = helper.holder?.getView<ImageView>(R.id.ivFile)
        if (item?.type!=1){
            helper.holder?.setText(R.id.tvFileName, item?.folderName)
            imageView?.setImageResource(R.drawable.folder)
        }else{
            helper.holder?.setText(R.id.tvFileName, item?.fileName)
            if (imageView != null) {
                Glide.with(mContext).load(item?.thumbnailURL).into(imageView)
            }
        }
    }


}