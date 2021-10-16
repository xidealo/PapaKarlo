package com.bunbeauty.papakarlo.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bunbeauty.papakarlo.ui.fragment.menu.ProductTabFragment

class ProductsPagerAdapter(
    private val productTabFragment: List<ProductTabFragment>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle)  {

    override fun createFragment(position: Int): Fragment {
        return productTabFragment[position]
    }

    override fun getItemCount(): Int = productTabFragment.size
}