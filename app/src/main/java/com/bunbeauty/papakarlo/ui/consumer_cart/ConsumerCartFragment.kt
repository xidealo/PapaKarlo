package com.bunbeauty.papakarlo.ui.consumer_cart

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.databinding.FragmentConsumerCartBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.ui.adapter.CartProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import com.bunbeauty.papakarlo.ui.consumer_cart.ConsumerCartFragmentDirections.backToMenuFragment
import com.bunbeauty.papakarlo.ui.consumer_cart.ConsumerCartFragmentDirections.toCreationOrder
import com.bunbeauty.papakarlo.view_model.ConsumerCartViewModel
import javax.inject.Inject

class ConsumerCartFragment : BarsFragment<FragmentConsumerCartBinding, ConsumerCartViewModel>() {

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var cartProductsAdapter: CartProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe(viewModel.cartProductListLiveData) { cartProductList ->
            cartProductsAdapter.setItemList(cartProductList.sortedBy { it.menuProduct.name })
        }
        subscribe(viewModel.deliveryStringLiveData) { deliveryString ->
            viewDataBinding.fragmentConsumerCartTvDeliveryInfo.text = deliveryString
        }
        subscribe(viewModel.isCartEmptyLiveData) { isCartEmpty ->
            viewDataBinding.fragmentConsumerCartGroupEmptyCart.toggleVisibility(isCartEmpty)
            viewDataBinding.fragmentConsumerCartGroupNotEmptyCart.toggleVisibility(!isCartEmpty)
        }

        setupRecyclerView()
        viewDataBinding.fragmentConsumerCartBtnMenu.setOnClickListener {
            viewModel.onMenuClicked()
        }
        viewDataBinding.fragmentConsumerCartBtnCrateOrder.setOnClickListener {
            viewModel.onCreateOrderClicked()
        }
    }

    private fun setupRecyclerView() {
        cartProductsAdapter.consumerCartViewModel = viewModel
        viewDataBinding.fragmentConsumerCartRvResult.adapter = cartProductsAdapter
    }

}