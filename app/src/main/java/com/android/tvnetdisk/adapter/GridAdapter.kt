package com.android.tvnetdisk.adapter

import androidx.fragment.app.FragmentActivity
import com.android.tvnetdisk.R
import com.android.tvnetdisk.entity.GridEntity
import com.owen.adapter.CommonRecyclerViewAdapter
import com.owen.adapter.CommonRecyclerViewHolder

class GridAdapter(context: FragmentActivity?) : CommonRecyclerViewAdapter<GridEntity>(context) {
    override fun getItemLayoutId(viewType: Int): Int {
        return R.layout.item_grid_layout
    }

    override fun onBindItemHolder(
        helper: CommonRecyclerViewHolder,
        item: GridEntity,
        position: Int
    ) {
        helper.holder.setText(R.id.tvFileName, item.text)
    }
}