package com.bunbeauty.papakarlo.ui.consumer_cart

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentConsumerCartBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.adapter.CartProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.consumer_cart.ConsumerCartFragmentDirections.actionCartFragmentToCreationOrder
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.view_model.ConsumerCartViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ConsumerCartFragment : BaseFragment<FragmentConsumerCartBinding, ConsumerCartViewModel>(),
    ConsumerCartNavigator {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_consumer_cart
    override var viewModelClass = ConsumerCartViewModel::class.java

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var cartProductsAdapter: CartProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigator = WeakReference(this)
        setupRecyclerView()

        viewModel.cartProductListLiveData.observe(viewLifecycleOwner) { cartProductList ->
            cartProductsAdapter.setItemList(cartProductList)
        }
    }

    private fun setupRecyclerView() {
        cartProductsAdapter.consumerCartViewModel = viewModel
        viewDataBinding.fragmentConsumerCartRvResult.adapter = cartProductsAdapter
    }

    override fun goToOrder() {
        findNavController().navigate(actionCartFragmentToCreationOrder())
    }

    override fun showMessage(message: String) {
        (activity as MainActivity).showMessage(message)
    }

}