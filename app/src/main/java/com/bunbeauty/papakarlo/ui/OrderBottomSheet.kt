package com.bunbeauty.papakarlo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.extensions.gone
import com.bunbeauty.domain.product.IProductHelper
import com.bunbeauty.domain.string_helper.IStringHelper
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetOrderBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.CafeOptionsViewModel
import com.bunbeauty.papakarlo.presentation.OrderViewModel
import com.bunbeauty.papakarlo.ui.OrderBottomSheetArgs.fromBundle
import com.bunbeauty.papakarlo.ui.adapter.CartProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog
import javax.inject.Inject

class OrderBottomSheet : BaseBottomSheetDialog<BottomSheetOrderBinding>() {

    override var layoutId = R.layout.bottom_sheet_order
    override val viewModel: OrderViewModel by viewModels { modelFactory }

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

        cartProductsAdapter.canBeChanged = false
        viewDataBinding.bottomSheetOrderRvCartProducts.adapter = cartProductsAdapter
        cartProductsAdapter.setItemList(fromBundle(requireArguments()).order.cartProducts)
    }
}