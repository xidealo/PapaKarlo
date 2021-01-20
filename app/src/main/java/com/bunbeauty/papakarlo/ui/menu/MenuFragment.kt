package com.bunbeauty.papakarlo.ui.menu

import android.os.Bundle
import android.view.View
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.databinding.FragmentMenuBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.ui.adapter.ProductsPagerAdapter
import com.bunbeauty.papakarlo.ui.base.TopBarFragment
import com.bunbeauty.papakarlo.ui.products.ProductsFragment
import com.bunbeauty.papakarlo.view_model.MenuViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MenuFragment : TopBarFragment<FragmentMenuBinding, MenuViewModel>() {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_menu
    override var viewModelClass = MenuViewModel::class.java
    override lateinit var title: String

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = resources.getString(R.string.title_menu)

        super.onViewCreated(view, savedInstanceState)

        viewModel.getMenuProductList()
        viewDataBinding.fragmentMenuVp.adapter = ProductsPagerAdapter(
            listOf(
                ProductsFragment.newInstance(ProductCode.ALL),
                ProductsFragment.newInstance(ProductCode.PIZZA),
                ProductsFragment.newInstance(ProductCode.BURGER),
                ProductsFragment.newInstance(ProductCode.POTATO),
                ProductsFragment.newInstance(ProductCode.BARBECUE),
                ProductsFragment.newInstance(ProductCode.OVEN),
            ),
            requireActivity()
        )
        val tabNameList = arrayListOf(
            resources.getString(R.string.title_menu_all),
            resources.getString(R.string.title_menu_pizza),
            resources.getString(R.string.title_menu_burger),
            resources.getString(R.string.title_menu_potato),
            resources.getString(R.string.title_menu_barbecue),
            resources.getString(R.string.title_menu_oven),
        )
        val tabIconList = arrayListOf(
            R.drawable.ic_all_products,
            R.drawable.ic_pizza,
            R.drawable.ic_burger,
            R.drawable.ic_potato,
            R.drawable.ic_barbecue,
            R.drawable.ic_oven,
        )
        TabLayoutMediator(
            viewDataBinding.fragmentMenuTl,
            viewDataBinding.fragmentMenuVp
        ) { tab, i ->
            tab.setIcon(tabIconList[i])
            tab.text = tabNameList[i]
        }.attach()
    }

    companion object {
        const val TAG = "MenuFragment"

        @JvmStatic
        fun newInstance(menuProducts: ArrayList<MenuProduct>) = MenuFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(MenuProduct.PRODUCTS, menuProducts)
            }
        }
    }
}