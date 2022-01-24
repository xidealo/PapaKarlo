package com.bunbeauty.papakarlo.ui.fragment.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentConsumerCartBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.strikeOutText
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.cart.ConsumerCartViewModel
import com.bunbeauty.papakarlo.presentation.state.State
import com.bunbeauty.papakarlo.ui.adapter.CartProductAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.decorator.MarginItemVerticalDecoration
import com.bunbeauty.presentation.item.CartProductItem
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ConsumerCartFragment : BaseFragment(R.layout.fragment_consumer_cart) {

    @Inject
    lateinit var cartProductAdapter: CartProductAdapter

    @Inject
    lateinit var marginItemVerticalDecoration: MarginItemVerticalDecoration

    override val viewModel: ConsumerCartViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(FragmentConsumerCartBinding::bind)

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupProductList()
        viewBinding.run {
            fragmentConsumerCartBtnMenu.setOnClickListener {
                viewModel.onMenuClicked()
            }
            fragmentConsumerCartBtnCrateOrder.setOnClickListener {
                viewModel.onCreateOrderClicked()
            }
            fragmentConsumerCartTvOldTotalCost.strikeOutText()
            viewModel.deliveryInfo.onEach { deliveryString ->
                fragmentConsumerCartTvDeliveryInfo.text = deliveryString
            }.startedLaunch()
            viewModel.oldTotalCost.onEach { oldTotalCost ->
                fragmentConsumerCartTvOldTotalCost.text = oldTotalCost
            }.startedLaunch()
            viewModel.newTotalCost.onEach { newTotalCost ->
                fragmentConsumerCartTvNewTotalCost.text = newTotalCost
            }.startedLaunch()
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
        cartProductAdapter.setOnItemClickListener { cartProductItem ->
            viewModel.onProductClicked(cartProductItem)
        }
        viewBinding.run {
            fragmentConsumerCartRvResult.addItemDecoration(marginItemVerticalDecoration)
            fragmentConsumerCartRvResult.adapter = cartProductAdapter

            viewModel.orderProductListState.onEach { state ->
                fragmentConsumerCartGroupEmptyCart.toggleVisibility(state is State.Empty)
                fragmentConsumerCartGroupNotEmptyCart.toggleVisibility(state is State.Success)
                fragmentConsumerCartPbLoading.toggleVisibility(state is State.Loading)

                if (state is State.Success) {
                    cartProductAdapter.submitList(state.data)
                }
            }.startedLaunch()
        }
    }

    override fun onDestroyView() {
        viewBinding.fragmentConsumerCartRvResult.adapter = null

        super.onDestroyView()
    }
}