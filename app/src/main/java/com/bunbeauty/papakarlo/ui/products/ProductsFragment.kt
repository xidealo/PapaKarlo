package com.bunbeauty.papakarlo.ui.products

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.databinding.FragmentProductsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.data.enums.ProductCode
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.ui.adapter.MenuProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.menu.MenuFragmentDirections.toProductFragment
import com.bunbeauty.papakarlo.view_model.ProductsViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ProductsFragment : BaseFragment<FragmentProductsBinding, ProductsViewModel>() {

    @Inject
    lateinit var menuProductsAdapter: MenuProductsAdapter

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel.productCode = requireArguments().getParcelable(com.bunbeauty.data.model.MenuProduct.PRODUCT_CODE)!!
        subscribe(viewModel.isLoadingLiveData) { isLoading ->
            viewDataBinding.activityMainPbLoading.toggleVisibility(isLoading)
        }
        subscribe(viewModel.productListLiveData) { productList ->
            menuProductsAdapter.setItemList(productList)
            viewDataBinding.fragmentProductsRvResult.smoothScrollToPosition(0)
        }
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