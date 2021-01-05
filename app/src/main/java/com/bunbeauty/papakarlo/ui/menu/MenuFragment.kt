package com.bunbeauty.papakarlo.ui.menu

import android.os.Bundle
import android.util.Log
import android.view.View
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.local.datastore.DataStoreHelper
import com.bunbeauty.papakarlo.data.model.ContactInfo
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.databinding.FragmentMenuBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.ui.adapter.ProductsPagerAdapter
import com.bunbeauty.papakarlo.ui.base.TopBarFragment
import com.bunbeauty.papakarlo.ui.products.ProductsFragment
import com.bunbeauty.papakarlo.view_model.MenuViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MenuFragment : TopBarFragment<FragmentMenuBinding, MenuViewModel>() {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_menu
    override var viewModelClass = MenuViewModel::class.java

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = resources.getString(R.string.title_menu)

        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.fragmentMenuVp.adapter = ProductsPagerAdapter(
            listOf(
                ProductsFragment.newInstance(ProductCode.All),
                ProductsFragment.newInstance(ProductCode.Pizza),
                ProductsFragment.newInstance(ProductCode.Hamburger),
                ProductsFragment.newInstance(ProductCode.Potato),
                ProductsFragment.newInstance(ProductCode.OnCoals),
            ),
            requireActivity()
        )
        val tabNameList = arrayListOf(
            resources.getString(R.string.title_menu_all),
            resources.getString(R.string.title_menu_pizza),
            resources.getString(R.string.title_menu_burger),
            resources.getString(R.string.title_menu_potato),
            resources.getString(R.string.title_menu_onCoals)
        )
        val tabIconList = arrayListOf(
            R.drawable.ic_all_products,
            R.drawable.ic_pizza,
            R.drawable.ic_burger,
            R.drawable.ic_french_fries,
            R.drawable.ic_kebab
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