package com.bunbeauty.papakarlo.ui.view

import android.os.Bundle
import android.view.View
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.databinding.FragmentMenuBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.ui.ProductsFragment
import com.bunbeauty.papakarlo.ui.adapter.ProductsPagerAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.view_model.MenuViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>() {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_menu
    override var viewModelClass = MenuViewModel::class.java

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    lateinit var menuProducts: ArrayList<MenuProduct>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            menuProducts = it.getParcelableArrayList(MenuProduct.PRODUCTS)!!
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("Меню")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewDataBinding.fragmentMenuVp.adapter = ProductsPagerAdapter(
            listOf(
                ProductsFragment.newInstance(ProductCode.All, menuProducts),
                ProductsFragment.newInstance(ProductCode.Pizza, menuProducts),
                ProductsFragment.newInstance(ProductCode.Hamburger, menuProducts),
                ProductsFragment.newInstance(ProductCode.Potato, menuProducts),
                ProductsFragment.newInstance(ProductCode.OnCoals, menuProducts),
            ),
            requireActivity()
        )

        val tabNameList = arrayListOf(
            resources.getString(R.string.title_menu_all),
            resources.getString(R.string.title_menu_pizza),
            resources.getString(R.string.title_menu_hamburger),
            resources.getString(R.string.title_menu_potato),
            resources.getString(R.string.title_menu_onCoals)
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
        fun newInstance(menuProducts: ArrayList<MenuProduct>) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(MenuProduct.PRODUCTS, menuProducts)
                }
            }
    }
}