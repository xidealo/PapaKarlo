package com.bunbeauty.papakarlo.ui.consumer_cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentConsumerCartBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.visible
import com.bunbeauty.papakarlo.ui.adapter.CartProductsAdapter
import com.bunbeauty.papakarlo.ui.base.CartClickableFragment
import com.bunbeauty.papakarlo.ui.consumer_cart.ConsumerCartFragmentDirections.toCreationOrder
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderFragmentDirections.backToMainFragment
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.view_model.ConsumerCartViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ConsumerCartFragment : CartClickableFragment<FragmentConsumerCartBinding, ConsumerCartViewModel>() {

    override lateinit var title: String

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var cartProductsAdapter: CartProductsAdapter

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = resources.getString(R.string.title_cart)

        super.onViewCreated(view, savedInstanceState)

        viewModel.cartProductListLiveData.observe(viewLifecycleOwner) { cartProductList ->
            cartProductsAdapter.setItemList(cartProductList.sortedBy { it.menuProduct.name })
        }
        viewModel.cartLiveData.observe(viewLifecycleOwner) {
            viewDataBinding.fragmentConsumerCartBtnCrateOrder.text = "Оформить заказ на $it"
        }

        setupRecyclerView()
        viewDataBinding.viewModel = viewModel
        viewDataBinding.fragmentConsumerCartBtnMenu.setOnClickListener {
            goToMenu()
        }
        viewDataBinding.fragmentConsumerCartBtnCrateOrder.setOnClickListener {
            goToOrder()
        }
    }

    private fun setupRecyclerView() {
        cartProductsAdapter.consumerCartViewModel = viewModel
        viewDataBinding.fragmentConsumerCartRvResult.adapter = cartProductsAdapter
    }

    override fun goToCart(view: View) {
    }

    private fun goToMenu() {
        findNavController().navigate(backToMainFragment())
    }

    private fun goToOrder() {
        findNavController().navigate(toCreationOrder())
    }

}