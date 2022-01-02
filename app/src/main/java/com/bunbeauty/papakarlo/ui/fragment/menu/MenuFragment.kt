package com.bunbeauty.papakarlo.ui.fragment.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentMenuBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.menu.MenuViewModel
import com.bunbeauty.papakarlo.ui.MainActivity
import com.bunbeauty.papakarlo.ui.adapter.CategoryAdapter
import com.bunbeauty.papakarlo.ui.adapter.MenuProductAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.decorator.MarginItemHorizontalDecoration
import com.bunbeauty.papakarlo.ui.decorator.MarginItemVerticalDecoration
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MenuFragment : BaseFragment<FragmentMenuBinding>() {

    override val viewModel: MenuViewModel by viewModels { viewModelFactory }

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
        disableBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.run {
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
                (fragmentMenuRvProducts.layoutManager as LinearLayoutManager)
                    .scrollToPositionWithOffset(viewModel.getMenuPosition(categoryItem), 0)
            }

            fragmentMenuRvProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy != 0) {
                        val position =
                            (fragmentMenuRvProducts.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        viewModel.checkSelectedCategory(position)
                    }
                }
            })

            (activity as? MainActivity)?.findViewById<BottomNavigationView>(R.id.activity_main_bnv_bottom_navigation)
                ?.setOnItemReselectedListener {
                    fragmentMenuRvProducts.smoothScrollToPosition(0)
                }

            viewModel.categoryList.onEach { categoryList ->
                categoryAdapter.submitList(categoryList)
            }.startedLaunch()
            viewModel.menuList.onEach { menuList ->
                menuProductAdapter.submitList(menuList)
            }.startedLaunch()
            viewModel.categoryPosition.onEach { categoryPosition ->
                fragmentMenuRvCategories.scrollToPosition(categoryPosition)
            }.startedLaunch()
        }
    }
}