package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.databinding.FragmentProductBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.view_model.ProductViewModel
import java.lang.ref.WeakReference

class ProductFragment : BaseFragment<FragmentProductBinding, ProductViewModel>(), ProductNavigator {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_product
    override var viewModelClass = ProductViewModel::class.java

    lateinit var menuProduct: MenuProduct

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            menuProduct = it.getParcelable(MenuProduct.PRODUCT)!!
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("${menuProduct.name}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewDataBinding.product = menuProduct
        viewModel.productNavigator = WeakReference(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun addWishProduct(menuProduct: MenuProduct) {
        (activity as MainActivity).viewModel.addCartProduct(menuProduct)
        (activity as MainActivity).showMessage(
            "Вы добавили ${menuProduct.name} в корзину",
            viewDataBinding.fragmentProductClMain
        )
    }

    companion object {
        const val TAG = "ProductFragment"

        @JvmStatic
        fun newInstance(menuProduct: MenuProduct) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MenuProduct.PRODUCT, menuProduct)
                }
            }
    }
}