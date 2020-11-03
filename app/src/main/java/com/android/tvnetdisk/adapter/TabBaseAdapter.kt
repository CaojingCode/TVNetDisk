package com.android.tvnetdisk.adapter

import android.content.Context
import com.android.tvnetdisk.R
import com.android.tvnetdisk.entity.ColumnEntity
import com.android.tvnetdisk.entity.TabEntity
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
    }
}