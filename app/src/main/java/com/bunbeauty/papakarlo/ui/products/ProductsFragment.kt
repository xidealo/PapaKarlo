package com.bunbeauty.papakarlo.ui.products

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.databinding.FragmentProductsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.ui.adapter.MenuProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.main.MainFragmentDirections
import com.bunbeauty.papakarlo.view_model.ProductsViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ProductsFragment : BaseFragment<FragmentProductsBinding, ProductsViewModel>(),
    ProductsNavigator {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_products
    override var viewModelClass = ProductsViewModel::class.java
    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var menuProductsAdapter: MenuProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            viewModel.productCode = it.getParcelable(MenuProduct.PRODUCT_CODE)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel.navigator = WeakReference(this)
        viewModel.productListLiveData.observe(viewLifecycleOwner) { productList ->
            menuProductsAdapter.setItemList(productList)
        }
    }

    private fun setupRecyclerView() {
        menuProductsAdapter.productsViewModel = viewModel
        menuProductsAdapter.productsFragment = this
        viewDataBinding.fragmentProductsRvResult.adapter = menuProductsAdapter
    }

    override fun goToProduct(menuProduct: MenuProduct) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToProductFragment(menuProduct)
        )
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