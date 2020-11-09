package com.android.tvnetdisk.adapter

import android.content.Context
import android.widget.ImageView
import com.android.tvnetdisk.R
import com.android.tvnetdisk.entity.CampusResourcesEntity
import com.bumptech.glide.Glide
import com.owen.adapter.CommonRecyclerViewAdapter
import com.owen.adapter.CommonRecyclerViewHolder

class PreViewAdapter(context: Context) : CommonRecyclerViewAdapter<CampusResourcesEntity>(context)   {
    override fun getItemLayoutId(viewType: Int): Int {
        return R.layout.item_preview
    }

    override fun onBindItemHolder(
        helper: CommonRecyclerViewHolder?,
        item: CampusResourcesEntity?,
        position: Int
    ) {
      var imageView=  helper?.holder?.getView<ImageView>(R.id.ivPreView)
        if (imageView != null) {
            Glide.with(mContext).load(item?.fileURL).into(imageView)
        }
    }
}