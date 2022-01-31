package com.bunbeauty.papakarlo.feature.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.decorator.MarginItemHorizontalDecoration
import com.bunbeauty.papakarlo.common.decorator.MarginItemVerticalDecoration
import com.bunbeauty.papakarlo.databinding.FragmentMenuBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.scrollToPositionWithOffset
import com.bunbeauty.papakarlo.feature.main.MainActivity
import com.bunbeauty.papakarlo.feature.menu.category.CategoryAdapter
import com.bunbeauty.papakarlo.feature.menu.menu_product.MenuProductAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MenuFragment : BaseFragment(R.layout.fragment_menu) {

    override val viewModel: MenuViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(FragmentMenuBinding::bind)

    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    @Inject
    lateinit var menuProductAdapter: MenuProductAdapter

    @Inject
    lateinit var marginItemHorizontalDecoration: MarginItemHorizontalDecoration

    @Inject
    lateinit var marginItemVerticalDecoration: MarginItemVerticalDecoration

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewBinding.run {
            fragmentMenuRvCategories.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            fragmentMenuRvCategories.addItemDecoration(marginItemHorizontalDecoration)
            fragmentMenuRvCategories.adapter = categoryAdapter

            fragmentMenuRvProducts.addItemDecoration(marginItemVerticalDecoration)
            fragmentMenuRvProducts.adapter = menuProductAdapter
            menuProductAdapter.setOnItemClickListener { menuItem ->
                viewModel.onMenuItemClicked(menuItem)
            }
            menuProductAdapter.setOnButtonClickListener { menuProductItem ->
                viewModel.onAddProductClicked(menuProductItem)
            }

            categoryAdapter.setOnItemClickListener { categoryItem ->
                viewModel.onCategorySelected(categoryItem.uuid)
            }
            viewModel.menuPosition.startedLaunch { menuPosition ->
                fragmentMenuRvProducts.scrollToPositionWithOffset(menuPosition, 0)
            }

            fragmentMenuRvProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy != 0) {
                        val position =
                            (fragmentMenuRvProducts.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        viewModel.onMenuPositionChanged(position)
                    }
                }
            })

            (activity as? MainActivity)?.findViewById<BottomNavigationView>(R.id.activity_main_bnv_bottom_navigation)
                ?.setOnItemReselectedListener {
                    fragmentMenuRvProducts.smoothScrollToPosition(0)
                }

            viewModel.categoryList.startedLaunch { categoryList ->
                categoryAdapter.submitList(categoryList)
            }
            viewModel.menuList.startedLaunch { menuList ->
                menuProductAdapter.submitList(menuList)
            }
            viewModel.categoryPosition.startedLaunch { categoryPosition ->
                fragmentMenuRvCategories.scrollToPosition(categoryPosition)
            }
        }
    }
}