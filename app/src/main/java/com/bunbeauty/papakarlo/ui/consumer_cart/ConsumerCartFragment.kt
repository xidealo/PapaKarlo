package com.bunbeauty.papakarlo.ui.consumer_cart

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.databinding.FragmentConsumerCartBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.adapter.CartProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderFragment
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.view_model.ConsumerCartViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ConsumerCartFragment : BaseFragment<FragmentConsumerCartBinding, ConsumerCartViewModel>(),
    ConsumerCartNavigator {

    override var title: String = "Корзина"
    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_consumer_cart
    override var viewModelClass = ConsumerCartViewModel::class.java

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }
    var cartProducts: ArrayList<CartProduct> = arrayListOf()

    @Inject
    lateinit var cartProductsAdapter: CartProductsAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as MainActivity).hideBottomPanel()
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel.consumerCartNavigator = WeakReference(this)
        cartProductsAdapter.consumerCartViewModel = viewModel
        viewModel.cartProductListLiveData.observe(viewLifecycleOwner) { cartProductList ->
            cartProductsAdapter.setItemList(cartProductList)
            cartProducts.clear()
            cartProducts.addAll(cartProductList)
        }
    }

    private fun setupRecyclerView() {
        viewDataBinding.fragmentConsumerCartRvResult.adapter = cartProductsAdapter
    }

    override fun goToOrder() {
        parentFragmentManager.beginTransaction()
            .replace(
                (activity as MainActivity).viewDataBinding.activityProductMenuClFragment.id,
                CreationOrderFragment.newInstance(cartProducts)
            )
            .addToBackStack(CreationOrderFragment.TAG)
            .commit()
    }

    companion object {
        const val TAG = "ConsumerCartFragment"

        @JvmStatic
        fun newInstance() =
            ConsumerCartFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}