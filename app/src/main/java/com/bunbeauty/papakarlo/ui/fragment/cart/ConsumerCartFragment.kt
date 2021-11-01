package com.bunbeauty.papakarlo.ui.fragment.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.State
import com.bunbeauty.papakarlo.databinding.FragmentConsumerCartBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.extensions.strikeOutText
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.cart.ConsumerCartViewModel
import com.bunbeauty.papakarlo.ui.adapter.CartProductAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.custom.MarginItemDecoration
import com.bunbeauty.presentation.item.CartProductItem
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ConsumerCartFragment : BaseFragment<FragmentConsumerCartBinding>() {

    @Inject
    lateinit var cartProductAdapter: CartProductAdapter

    @Inject
    lateinit var marginItemDecoration: MarginItemDecoration

    override val viewModel: ConsumerCartViewModel by viewModels { viewModelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupProductList()
        viewDataBinding.run {
            fragmentConsumerCartBtnMenu.setOnClickListener {
                viewModel.onMenuClicked()
            }
            fragmentConsumerCartBtnCrateOrder.setOnClickListener {
                viewModel.onCreateOrderClicked()
            }
            fragmentConsumerCartTvOldTotalCost.strikeOutText()
            viewModel.deliveryInfo.onEach { deliveryString ->
                fragmentConsumerCartTvDeliveryInfo.text = deliveryString
            }.startedLaunch(viewLifecycleOwner)
            viewModel.oldTotalCost.onEach { oldTotalCost ->
                fragmentConsumerCartTvOldTotalCost.text = oldTotalCost
            }.startedLaunch(viewLifecycleOwner)
            viewModel.cartCost.onEach { cartCost ->
                fragmentConsumerCartTvNewTotalCost.text = cartCost
            }.startedLaunch(viewLifecycleOwner)
        }
    }

    private fun setupProductList() {
        cartProductAdapter.countChangeListener =
            object : CartProductAdapter.ItemCountChangeListener {
                override fun onItemCountIncreased(item: CartProductItem) {
                    viewModel.addProductToCart(item.menuProductUuid)
                }

                override fun onItemCountDecreased(item: CartProductItem) {
                    viewModel.removeProductFromCart(item.menuProductUuid)
                }
            }
        viewDataBinding.run {
            fragmentConsumerCartRvResult.addItemDecoration(marginItemDecoration)
            fragmentConsumerCartRvResult.adapter = cartProductAdapter

            viewModel.orderProductListState.onEach { state ->
                fragmentConsumerCartGroupEmptyCart.toggleVisibility(state is State.Empty)
                fragmentConsumerCartGroupNotEmptyCart.toggleVisibility(state is State.Success)
                fragmentConsumerCartPbLoading.toggleVisibility(state is State.Loading)

                if (state is State.Success) {
                    cartProductAdapter.submitList(state.data)
                }
            }.startedLaunch(viewLifecycleOwner)
        }
    }

    override fun onDestroyView() {
        viewDataBinding.fragmentConsumerCartRvResult.adapter = null

        super.onDestroyView()
    }
}