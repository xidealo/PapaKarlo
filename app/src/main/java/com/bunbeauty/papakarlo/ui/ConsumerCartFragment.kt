package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.State
import com.bunbeauty.papakarlo.databinding.FragmentConsumerCartBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.extensions.visible
import com.bunbeauty.papakarlo.presentation.cart.ConsumerCartViewModel
import com.bunbeauty.papakarlo.ui.adapter.CartProductAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.presentation.view_model.base.adapter.CartProductItem
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ConsumerCartFragment : BaseFragment<FragmentConsumerCartBinding>() {

    @Inject
    lateinit var cartProductAdapter: CartProductAdapter

    override val viewModel: ConsumerCartViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartProductAdapter.countChangeListener =
            object : CartProductAdapter.ItemCountChangeListener {
                override fun onItemCountIncreased(item: CartProductItem) {
                    Log.d("test", "onItemCountIncreased " + item.uuid)
                    viewModel.addProductToCart(item.menuProductUuid)
                }

                override fun onItemCountDecreased(item: CartProductItem) {
                    Log.d("test", "onItemCountDecreased " + item.uuid)
                    viewModel.removeProductFromCart(item.menuProductUuid)
                }
            }
        viewDataBinding.run {
            fragmentConsumerCartRvResult.adapter = cartProductAdapter
            fragmentConsumerCartBtnMenu.setOnClickListener {
                viewModel.onMenuClicked()
            }
            fragmentConsumerCartBtnCrateOrder.setOnClickListener {
                viewModel.onCreateOrderClicked()
            }
            viewModel.cartProductListState.onEach { state ->
                when (state) {
                    is State.Empty -> {
                        fragmentConsumerCartGroupEmptyCart.visible()
                        fragmentConsumerCartGroupNotEmptyCart.gone()
                        fragmentConsumerCartPbLoading.gone()
                    }
                    is State.Success -> {
                        fragmentConsumerCartGroupEmptyCart.gone()
                        fragmentConsumerCartGroupNotEmptyCart.visible()
                        fragmentConsumerCartPbLoading.gone()
                        cartProductAdapter.submitList(state.data)
                    }
                    is State.Loading -> {
                        fragmentConsumerCartGroupEmptyCart.gone()
                        fragmentConsumerCartGroupNotEmptyCart.gone()
                        fragmentConsumerCartPbLoading.visible()
                    }
                    else -> Unit
                }
            }.startedLaunch(viewLifecycleOwner)
            viewModel.deliveryInfo.onEach { deliveryString ->
                fragmentConsumerCartTvDeliveryInfo.text = deliveryString
            }.startedLaunch(viewLifecycleOwner)
        }
    }
}