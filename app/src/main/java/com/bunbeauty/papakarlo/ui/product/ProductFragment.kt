package com.bunbeauty.papakarlo.ui.product

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.os.Bundle
import android.view.View
import com.bunbeauty.papakarlo.databinding.FragmentProductBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.invisible
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import com.bunbeauty.papakarlo.ui.product.ProductFragmentArgs.fromBundle
import com.bunbeauty.papakarlo.utils.product.IProductHelper
import com.bunbeauty.papakarlo.utils.string.IStringHelper
import com.bunbeauty.papakarlo.view_model.EmptyViewModel
import javax.inject.Inject

class ProductFragment : BarsFragment<FragmentProductBinding, EmptyViewModel>() {

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var stringHelper: IStringHelper

    @Inject
    lateinit var productHelper: IProductHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuProduct = fromBundle(requireArguments()).menuProduct
        if (menuProduct.weight == 0)
            viewDataBinding.fragmentProductTvWeight.invisible()

        viewDataBinding.product = menuProduct
        viewDataBinding.stringHelper = stringHelper
        viewDataBinding.productHelper = productHelper
        viewDataBinding.fragmentProductTvOldCost.paintFlags =
            viewDataBinding.fragmentProductTvOldCost.paintFlags or STRIKE_THRU_TEXT_FLAG
        viewDataBinding.fragmentProductBtnAdd.setOnClickListener {
            viewModel.addProductToCart(menuProduct)
            showMessage("${menuProduct.name} был добавлен в корзину")
        }
    }
}