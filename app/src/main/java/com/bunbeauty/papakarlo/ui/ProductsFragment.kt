package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.databinding.FragmentProductsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.ui.adapter.MenuProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.view_model.ProductsViewModel
import javax.inject.Inject

class ProductsFragment : BaseFragment<FragmentProductsBinding, ProductsViewModel>() {

    override var title: String = "Меню"
    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_products
    override var viewModelClass = ProductsViewModel::class.java

    private lateinit var productCode: ProductCode
    private lateinit var menuProducts: ArrayList<MenuProduct>

    @Inject
    lateinit var menuProductsAdapter: MenuProductsAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productCode = it.getParcelable(MenuProduct.PRODUCT_CODE)!!
            menuProducts = it.getParcelableArrayList(MenuProduct.PRODUCTS)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        viewModel.filterProducts(productCode, menuProducts)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRecyclerView() {
        menuProductsAdapter.productsFragment = this
        viewDataBinding.fragmentProductsRvResult.adapter = menuProductsAdapter
        viewDataBinding.fragmentProductsRvResult.layoutManager = linearLayoutManager
    }

    companion object {
        @JvmStatic
        fun newInstance(productCode: ProductCode, menuProducts: ArrayList<MenuProduct>) =
            ProductsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MenuProduct.PRODUCT_CODE, productCode)
                    putParcelableArrayList(MenuProduct.PRODUCTS, menuProducts)
                }
            }
    }

}