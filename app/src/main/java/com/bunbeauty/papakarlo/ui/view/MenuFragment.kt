package com.bunbeauty.papakarlo.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.Product
import com.bunbeauty.papakarlo.databinding.FragmentMenuBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.ui.ProductsFragment
import com.bunbeauty.papakarlo.ui.adapter.ProductsPagerAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.view_model.MenuViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>() {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_menu
    override var viewModelClass = MenuViewModel::class.java

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    lateinit var products: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            products = it.getParcelableArrayList(Product.PRODUCTS)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewDataBinding.fragmentMenuVp.adapter = ProductsPagerAdapter(
            listOf(
                ProductsFragment.newInstance(ProductCode.All, products),
                ProductsFragment.newInstance(ProductCode.Pizza, products),
                ProductsFragment.newInstance(ProductCode.Hamburger, products),
                ProductsFragment.newInstance(ProductCode.Potato, products),
                ProductsFragment.newInstance(ProductCode.OnCoals, products),
            ),
            requireActivity()
        )

        val tabNameList = arrayListOf(
            ProductCode.All.name,
            ProductCode.Pizza.name,
            ProductCode.Hamburger.name,
            ProductCode.Potato.name,
            ProductCode.OnCoals.name
        )

        TabLayoutMediator(
            viewDataBinding.fragmentMenuTl,
            viewDataBinding.fragmentMenuVp
        ) { tab, i ->
            tab.text = tabNameList[i]
        }.attach()

        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val TAG = "MenuFragment"

        @JvmStatic
        fun newInstance(products: ArrayList<Product>) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(Product.PRODUCTS, products)
                }
            }
    }
}