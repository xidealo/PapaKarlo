package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.Product
import com.bunbeauty.papakarlo.databinding.FragmentConsumerCartBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.view_model.ConsumerCartViewModel

class ConsumerCartFragment : BaseFragment<FragmentConsumerCartBinding, ConsumerCartViewModel>() {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_consumer_cart
    override var viewModelClass = ConsumerCartViewModel::class.java

    lateinit var wishProductList: ArrayList<Product>

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            wishProductList = it.getParcelableArrayList(Product.PRODUCTS)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewDataBinding.fragmentConsumerCartTvProductCount.text = wishProductList.size.toString()
        viewDataBinding.fragmentConsumerCartTvCommonPrice.text =
            wishProductList.sumBy { it.cost }.toString()

        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val TAG = "ConsumerCartFragment"

        @JvmStatic
        fun newInstance(wishProductList: ArrayList<Product>) =
            ConsumerCartFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(Product.PRODUCTS, wishProductList)
                }
            }
    }
}