package com.bunbeauty.papakarlo.ui

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.presentation.extensions.invisible
import com.bunbeauty.papakarlo.databinding.FragmentProductBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringHelper
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.EmptyViewModel
import com.bunbeauty.papakarlo.ui.ProductFragmentArgs.fromBundle
import javax.inject.Inject

class ProductFragment : BarsFragment<FragmentProductBinding>() {

    override var layoutId = R.layout.fragment_product
    override val viewModel: EmptyViewModel by viewModels { modelFactory }

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
        }
    }
}