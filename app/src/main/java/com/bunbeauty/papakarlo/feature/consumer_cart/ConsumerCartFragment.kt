package com.bunbeauty.papakarlo.feature.consumer_cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.decorator.MarginItemVerticalDecoration
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.databinding.FragmentConsumerCartBinding
import com.bunbeauty.papakarlo.extensions.strikeOutText
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConsumerCartFragment : BaseFragment(R.layout.fragment_consumer_cart) {

    val cartProductAdapter: CartProductAdapter by inject()
    val marginItemVerticalDecoration: MarginItemVerticalDecoration by inject()

    override val viewModel: ConsumerCartViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentConsumerCartBinding::bind)

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
            viewModel.deliveryInfo.startedLaunch { deliveryString ->
                fragmentConsumerCartTvDeliveryInfo.text = deliveryString
            }
            viewModel.oldTotalCost.startedLaunch { oldTotalCost ->
                fragmentConsumerCartTvOldTotalCost.text = oldTotalCost
            }
            viewModel.newTotalCost.startedLaunch { newTotalCost ->
                fragmentConsumerCartTvNewTotalCost.text = newTotalCost
            }
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

            viewModel.orderProductListState.startedLaunch { state ->
                fragmentConsumerCartGroupEmptyCart.toggleVisibility(state is State.Empty)
                fragmentConsumerCartGroupNotEmptyCart.toggleVisibility(state is State.Success)
                fragmentConsumerCartPbLoading.toggleVisibility(state is State.Loading)

                if (state is State.Success) {
                    cartProductAdapter.submitList(state.data)
                }
            }
        }
    }

    override fun onDestroyView() {
        viewBinding.fragmentConsumerCartRvResult.adapter = null

        super.onDestroyView()
    }
}