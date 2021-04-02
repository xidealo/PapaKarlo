package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bunbeauty.common.extensions.toggleVisibility
import com.bunbeauty.data.enums.ProductCode
import com.bunbeauty.papakarlo.databinding.FragmentProductsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.adapter.MenuProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.presentation.ProductsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProductsFragment : BaseFragment<FragmentProductsBinding, ProductsViewModel>() {

    @Inject
    lateinit var menuProductsAdapter: MenuProductsAdapter

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel.productCode =
            requireArguments().getParcelable(com.bunbeauty.data.model.MenuProduct.PRODUCT_CODE)!!
        viewModel.getProducts()
        viewModel.productListReceiver.onEach {
            viewDataBinding.activityMainPbLoading.toggleVisibility(false)
            menuProductsAdapter.setItemList(it)
            viewDataBinding.fragmentProductsRvResult.smoothScrollToPosition(0)
        }.launchWhenStarted(lifecycleScope)
    }

    private fun setupRecyclerView() {
        menuProductsAdapter.productsViewModel = viewModel
        menuProductsAdapter.productsFragment = this
        viewDataBinding.fragmentProductsRvResult.adapter = menuProductsAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(productCode: ProductCode) =
            ProductsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(com.bunbeauty.data.model.MenuProduct.PRODUCT_CODE, productCode)
                }
            }
    }

}