package com.bunbeauty.papakarlo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.bunbeauty.common.extensions.gone
import com.bunbeauty.domain.product.IProductHelper
import com.bunbeauty.domain.string_helper.IStringHelper
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetOrderBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.OrderViewModel
import com.bunbeauty.papakarlo.ui.OrderBottomSheetArgs.fromBundle
import com.bunbeauty.papakarlo.ui.adapter.CartProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog
import javax.inject.Inject

class OrderBottomSheet : BaseBottomSheetDialog<BottomSheetOrderBinding, OrderViewModel>() {

    override var layoutId = R.layout.bottom_sheet_order
    override var viewModelVariable = BR.viewModel

    @Inject
    lateinit var stringHelper: IStringHelper

    @Inject
    lateinit var productHelper: IProductHelper

    @Inject
    lateinit var cartProductsAdapter: CartProductsAdapter

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val order = fromBundle(requireArguments()).order
        viewDataBinding.stringHelper = stringHelper
        viewDataBinding.productHelper = productHelper
        viewDataBinding.order = order

        if (order.orderEntity.deferred.isEmpty()) {
            viewDataBinding.bottomSheetOrderGroupDeferrer.gone()
        }
        viewDataBinding.bottomSheetOrderRvCartProducts.setOnTouchListener { _, _ ->
            true
        }

        cartProductsAdapter.canBeChanged = false
        viewDataBinding.bottomSheetOrderRvCartProducts.adapter = cartProductsAdapter
        cartProductsAdapter.setItemList(fromBundle(requireArguments()).order.cartProducts)
    }
}