package com.bunbeauty.papakarlo.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bunbeauty.papakarlo.ui.products.ProductsFragment

class ProductsPagerAdapter(
    private val productsFragment: List<ProductsFragment>,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return productsFragment[position]
    }

    override fun getItemCount(): Int = productsFragment.size
}