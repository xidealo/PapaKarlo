package com.bunbeauty.papakarlo.ui

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.os.Bundle
import android.view.View
import com.bunbeauty.common.extensions.invisible
import com.bunbeauty.papakarlo.databinding.FragmentProductBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import com.bunbeauty.domain.product.IProductHelper
import com.bunbeauty.domain.string_helper.IStringHelper
import com.bunbeauty.papakarlo.presentation.EmptyViewModel
import com.bunbeauty.papakarlo.ui.ProductFragmentArgs.fromBundle
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