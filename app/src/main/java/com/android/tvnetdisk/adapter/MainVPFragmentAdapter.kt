package com.android.tvnetdisk.adapter

import android.view.View
import androidx.fragment.app.*
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainVPFragmentAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var fragments: ArrayList<Fragment> = arrayListOf()

    override fun getCount(): Int {
        return fragments.size
    }


    override fun getItem(position: Int): Fragment {

        return fragments[position]
    }
}