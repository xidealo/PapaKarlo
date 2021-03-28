package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import com.bunbeauty.domain.product.IProductHelper
import com.bunbeauty.domain.string_helper.IStringHelper
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetOrderBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.OrderViewModel
import com.bunbeauty.papakarlo.ui.adapter.CartProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog
import javax.inject.Inject

class OrderBottomSheet : BaseBottomSheetDialog<BottomSheetOrderBinding, OrderViewModel>() {

    override var layoutId = R.layout.bottom_sheet_order
    override var viewModelVariable = BR.viewModel

    @Inject
    lateinit var iStringHelper: IStringHelper

    @Inject
    lateinit var productHelper: IProductHelper

    @Inject
    lateinit var cartProductsAdapter: CartProductsAdapter

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.stringHelper = iStringHelper
        viewDataBinding.productHelper = productHelper
        viewDataBinding.order = OrderBottomSheetArgs.fromBundle(requireArguments()).order
        cartProductsAdapter.canBeChanged = false
        viewDataBinding.bottomSheetOrderRvCartProducts.adapter = cartProductsAdapter
        cartProductsAdapter.setItemList(OrderBottomSheetArgs.fromBundle(requireArguments()).order.cartProducts)
    }

}