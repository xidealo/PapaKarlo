package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.databinding.FragmentConsumerCartBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.adapter.CartProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.view_model.ConsumerCartViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ConsumerCartFragment : BaseFragment<FragmentConsumerCartBinding, ConsumerCartViewModel>(),
    ConsumerCartNavigator {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_consumer_cart
    override var viewModelClass = ConsumerCartViewModel::class.java

    lateinit var cartProductList: ArrayList<CartProduct>

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var cartProductsAdapter: CartProductsAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cartProductList = it.getParcelableArrayList(MenuProduct.PRODUCTS)!!
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("Корзина")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        viewModel.consumerCartNavigator = WeakReference(this)
        viewModel.setCartProducts(cartProductList)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRecyclerView() {
        viewDataBinding.fragmentConsumerCartRvResult.adapter = cartProductsAdapter
        viewDataBinding.fragmentConsumerCartRvResult.layoutManager = linearLayoutManager
    }

    override fun goToOrder() {
        parentFragmentManager.beginTransaction()
            .replace(
                (activity as MainActivity).viewDataBinding.activityProductMenuClFragment.id,
                CreationOrderFragment.newInstance()
            )
            .addToBackStack(CreationOrderFragment.TAG)
            .commit()
    }

    companion object {
        const val TAG = "ConsumerCartFragment"

        @JvmStatic
        fun newInstance(wishMenuProductList: Set<CartProduct>) =
            ConsumerCartFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(MenuProduct.PRODUCTS, ArrayList(wishMenuProductList))
                }
            }
    }
}