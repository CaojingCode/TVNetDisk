package com.android.tvnetdisk.adapter

import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import com.android.tvnetdisk.R
import com.android.tvnetdisk.entity.ColumnEntity
import com.android.tvnetdisk.entity.TabEntity
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.owen.adapter.CommonRecyclerViewAdapter
import com.owen.adapter.CommonRecyclerViewHolder

class TabBaseAdapter(context: Context) : CommonRecyclerViewAdapter<ColumnEntity>(context) {

    override fun getItemLayoutId(viewType: Int): Int {
        return R.layout.item_tab_layout
    }


    override fun onBindItemHolder(
        helper: CommonRecyclerViewHolder,
        item: ColumnEntity,
        position: Int
    ) {
        helper.holder.setText(R.id.tvTabTittle, item.columnName)
        if (item.isSelect) {
            helper.holder.setTextColor(R.id.tvTabTittle, Color.WHITE)
        } else {
            helper.holder.setTextColor(R.id.tvTabTittle, Color.BLACK)
        }
        var imageView = helper.holder?.getView<ImageView>(R.id.ivTag)
        if (imageView != null) {
            Glide.with(mContext).load(item.icon).into(imageView)
        }
    }
}