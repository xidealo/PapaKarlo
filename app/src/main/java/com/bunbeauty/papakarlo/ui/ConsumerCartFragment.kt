package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.databinding.FragmentConsumerCartBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.adapter.CartProductsAdapter
import com.bunbeauty.papakarlo.ui.adapter.MenuProductsAdapter
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.view_model.ConsumerCartViewModel
import javax.inject.Inject

class ConsumerCartFragment : BaseFragment<FragmentConsumerCartBinding, ConsumerCartViewModel>() {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_consumer_cart
    override var viewModelClass = ConsumerCartViewModel::class.java

    lateinit var wishMenuProductList: ArrayList<MenuProduct>

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
            wishMenuProductList = it.getParcelableArrayList(MenuProduct.PRODUCTS)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()

        viewDataBinding.fragmentConsumerCartTvProductCount.text =
            wishMenuProductList.size.toString()
        viewDataBinding.fragmentConsumerCartTvCommonPrice.text =
            wishMenuProductList.sumBy { it.cost }.toString()
        viewModel.setCartProducts(wishMenuProductList)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRecyclerView() {
        viewDataBinding.fragmentConsumerCartRvResult.adapter = cartProductsAdapter
        viewDataBinding.fragmentConsumerCartRvResult.layoutManager = linearLayoutManager
    }

    companion object {
        const val TAG = "ConsumerCartFragment"

        @JvmStatic
        fun newInstance(wishMenuProductList: ArrayList<MenuProduct>) =
            ConsumerCartFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(MenuProduct.PRODUCTS, wishMenuProductList)
                }
            }
    }
}