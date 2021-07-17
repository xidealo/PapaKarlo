package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.databinding.FragmentConsumerCartBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.ui.adapter.CartProductsAdapter
import com.bunbeauty.papakarlo.presentation.cart.ConsumerCartViewModel
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ConsumerCartFragment : BaseFragment<FragmentConsumerCartBinding>() {

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
            cartProductList.onEach { cartProductModelList ->
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
            }.startedLaunch(viewLifecycleOwner)

            deliveryStringFlow.onEach { deliveryString ->
                viewDataBinding.fragmentConsumerCartTvDeliveryInfo.text = deliveryString
            }.startedLaunch(viewLifecycleOwner)
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