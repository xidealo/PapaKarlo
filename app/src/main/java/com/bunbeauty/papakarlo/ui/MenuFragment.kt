package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.viewModels
import com.bunbeauty.data.enums.ProductCode
import com.bunbeauty.domain.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentMenuBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.EmptyViewModel
import com.bunbeauty.papakarlo.ui.adapter.ProductsPagerAdapter
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class MenuFragment : BarsFragment<FragmentMenuBinding>() {

    override var layoutId = R.layout.fragment_menu
    override val viewModel: EmptyViewModel by viewModels { modelFactory }
    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    override val isToolbarLogoVisible = true
    override val isBottomBarVisible = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.fragmentMenuTl.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    setIconColor(tab, R.color.colorPrimary)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    setIconColor(tab, R.color.mainTextColor)
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                fun setIconColor(tab: TabLayout.Tab?, @ColorRes colorId: Int) {
                    tab?.icon?.colorFilter =
                        BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                            resourcesProvider.getColor(colorId),
                            BlendModeCompat.SRC_ATOP
                        )
                }
            }
        )
        viewDataBinding.fragmentMenuVp.adapter = ProductsPagerAdapter(
            ProductCode.values().asList().map {
                ProductsFragment.newInstance(it)
            },
            requireActivity()
        )
        val tabNameList = arrayListOf(
            resources.getString(R.string.title_menu_all),
            resources.getString(R.string.title_menu_combo),
            resources.getString(R.string.title_menu_pizza),
            resources.getString(R.string.title_menu_barbecue),
            resources.getString(R.string.title_menu_burger),
            resources.getString(R.string.title_menu_drink),
            resources.getString(R.string.title_menu_potato),
            resources.getString(R.string.title_menu_spice),
            resources.getString(R.string.title_menu_bakery),
            resources.getString(R.string.title_menu_oven)
        )
        val tabIconList = arrayListOf(
            R.drawable.ic_all_products,
            R.drawable.ic_combo,
            R.drawable.ic_pizza,
            R.drawable.ic_barbecue,
            R.drawable.ic_burger,
            R.drawable.ic_drink,
            R.drawable.ic_potato,
            R.drawable.ic_spice,
            R.drawable.ic_bakery,
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
}