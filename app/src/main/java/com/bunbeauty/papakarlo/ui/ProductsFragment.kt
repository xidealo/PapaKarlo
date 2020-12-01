package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.Product
import com.bunbeauty.papakarlo.databinding.FragmentProductsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.ui.adapter.ProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.view_model.ProductsViewModel
import javax.inject.Inject

class ProductsFragment : BaseFragment<FragmentProductsBinding, ProductsViewModel>() {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_products
    override var viewModelClass = ProductsViewModel::class.java

    private lateinit var productCode: ProductCode

    @Inject
    lateinit var productsAdapter: ProductsAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productCode = it.getParcelable(Product.PRODUCT_CODE)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        viewModel.getProducts(productCode)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRecyclerView() {
        viewDataBinding.fragmentProductsRvResult.adapter = productsAdapter
        viewDataBinding.fragmentProductsRvResult.layoutManager = linearLayoutManager
    }

    companion object {
        @JvmStatic
        fun newInstance(productCode: ProductCode) =
            ProductsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Product.PRODUCT_CODE, productCode)
                }
            }
    }

}