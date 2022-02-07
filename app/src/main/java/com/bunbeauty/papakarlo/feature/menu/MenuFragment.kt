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
import com.bunbeauty.papakarlo.extensions.scrollToPositionWithOffset
import com.bunbeauty.papakarlo.feature.main.MainActivity
import com.bunbeauty.papakarlo.feature.menu.category.CategoryAdapter
import com.bunbeauty.papakarlo.feature.menu.menu_product.MenuProductAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : BaseFragment(R.layout.fragment_menu) {

    override val viewModel: MenuViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentMenuBinding::bind)

    val categoryAdapter: CategoryAdapter by inject()
    val menuProductAdapter: MenuProductAdapter by inject()
    val marginItemHorizontalDecoration: MarginItemHorizontalDecoration by inject()
    val marginItemVerticalDecoration: MarginItemVerticalDecoration by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewBinding.run {
            fragmentMenuRvCategories.run {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                addItemDecoration(marginItemHorizontalDecoration)
                adapter = categoryAdapter.apply {
                    setOnItemClickListener { categoryItem ->
                        viewModel.onCategorySelected(categoryItem.uuid)
                    }
                }
                viewModel.categoryPosition.startedLaunch { categoryPosition ->
                    scrollToPosition(categoryPosition)
                }
            }

            fragmentMenuRvProducts.run {
                addItemDecoration(marginItemVerticalDecoration)
                adapter = menuProductAdapter.apply {
                    setOnItemClickListener { menuItem ->
                        viewModel.onMenuItemClicked(menuItem)
                    }
                    setOnButtonClickListener { menuProductItem ->
                        viewModel.onAddProductClicked(menuProductItem)
                    }
                }
                viewModel.menuPosition.startedLaunch { menuPosition ->
                    scrollToPositionWithOffset(menuPosition, 0)
                }
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (dy != 0) {
                            val position =
                                (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                            viewModel.onMenuPositionChanged(position)
                        }
                    }
                })
                (activity as? MainActivity)?.findViewById<BottomNavigationView>(R.id.activity_main_bnv_bottom_navigation)
                    ?.setOnItemReselectedListener {
                        smoothScrollToPosition(0)
                    }
            }

            viewModel.categoryList.startedLaunch { categoryList ->
                categoryAdapter.submitList(categoryList)
            }
            viewModel.menuList.startedLaunch { menuList ->
                menuProductAdapter.submitList(menuList)
            }
        }
    }
}