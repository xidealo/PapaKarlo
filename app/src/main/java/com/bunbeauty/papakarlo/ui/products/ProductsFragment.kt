package com.bunbeauty.papakarlo.ui.products

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.databinding.FragmentProductsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.ui.adapter.MenuProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.main.MainFragmentDirections.actionMainFragmentToProductFragment
import com.bunbeauty.papakarlo.view_model.ProductsViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ProductsFragment : BaseFragment<FragmentProductsBinding, ProductsViewModel>(),
    ProductsNavigator {

    @Inject
    lateinit var menuProductsAdapter: MenuProductsAdapter

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel.productCode = requireArguments().getParcelable(MenuProduct.PRODUCT_CODE)!!
        viewModel.navigator = WeakReference(this)
        subscribe(viewModel.isLoadingLiveData) { isLoading ->
            viewDataBinding.activityMainPbLoading.toggleVisibility(isLoading)
        }
        subscribe(viewModel.productListLiveData) { productList ->
            menuProductsAdapter.setItemList(productList.sortedBy { it.name })
            viewDataBinding.fragmentProductsRvResult.smoothScrollToPosition(0)
        }
    }

    private fun setupRecyclerView() {
        menuProductsAdapter.productsViewModel = viewModel
        menuProductsAdapter.productsFragment = this
        viewDataBinding.fragmentProductsRvResult.adapter = menuProductsAdapter
    }

    override fun goToProduct(menuProduct: MenuProduct) {
        findNavController().navigate(actionMainFragmentToProductFragment(menuProduct))
    }

    companion object {
        @JvmStatic
        fun newInstance(productCode: ProductCode) =
            ProductsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MenuProduct.PRODUCT_CODE, productCode)
                }
            }
    }

}