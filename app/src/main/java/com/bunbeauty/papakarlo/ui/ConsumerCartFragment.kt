package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentConsumerCartBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.ui.adapter.CartProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import com.bunbeauty.papakarlo.presentation.ConsumerCartViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ConsumerCartFragment : BarsFragment<FragmentConsumerCartBinding>() {

    override var layoutId = R.layout.fragment_consumer_cart
    override val viewModel: ConsumerCartViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var cartProductsAdapter: CartProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartProductsAdapter.consumerCartViewModel = viewModel
        with(viewModel) {
            getCartProductModelFlowList.onEach { cartProductModelList ->
                if (cartProductModelList.isEmpty()) {
                    viewDataBinding.fragmentConsumerCartGroupEmptyCart.toggleVisibility(true)
                    viewDataBinding.fragmentConsumerCartGroupNotEmptyCart.toggleVisibility(false)
                } else {
                    viewDataBinding.fragmentConsumerCartGroupEmptyCart.toggleVisibility(false)
                    viewDataBinding.fragmentConsumerCartGroupNotEmptyCart.toggleVisibility(
                        true
                    )
                    cartProductsAdapter.submitList(cartProductModelList)
                }
                viewDataBinding.fragmentConsumerCartPbLoading.gone()
            }.launchWhenStarted(lifecycleScope)

            deliveryStringFlow.onEach { deliveryString ->
                viewDataBinding.fragmentConsumerCartTvDeliveryInfo.text = deliveryString
            }.launchWhenStarted(lifecycleScope)
        }
        with(viewDataBinding) {
            fragmentConsumerCartRvResult.adapter = cartProductsAdapter
            fragmentConsumerCartBtnMenu.setOnClickListener {
                viewModel.onMenuClicked()
            }
            fragmentConsumerCartBtnCrateOrder.setOnClickListener {
                viewModel.onCreateOrderClicked()
            }
        }
    }
}