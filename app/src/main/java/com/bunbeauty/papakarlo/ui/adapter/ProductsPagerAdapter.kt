package com.bunbeauty.papakarlo.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bunbeauty.papakarlo.ui.ProductTabFragment

class ProductsPagerAdapter(
    private val productTabFragment: List<ProductTabFragment>,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return productTabFragment[position]
    }

    override fun getItemCount(): Int = productTabFragment.size
}