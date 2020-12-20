package com.bunbeauty.papakarlo.ui.product

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.databinding.FragmentProductBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.base.TopBarFragment
import com.bunbeauty.papakarlo.ui.product.ProductFragmentDirections.actionProductFragmentToCartFragment
import com.bunbeauty.papakarlo.view_model.ProductViewModel
import java.lang.ref.WeakReference

class ProductFragment : TopBarFragment<FragmentProductBinding, ProductViewModel>(), ProductNavigator {

    override lateinit var title: String
    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_product
    override var viewModelClass = ProductViewModel::class.java

    lateinit var menuProduct: MenuProduct

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = ProductFragmentArgs.fromBundle(requireArguments()).menuProduct
        viewDataBinding.product = product
        viewModel.navigator = WeakReference(this)

        viewDataBinding.fragmentProductBtnAdd.setOnClickListener {
            viewModel.addProductToCart(product)
        }
    }

    override fun goToCart(view: View) {
        findNavController().navigate(actionProductFragmentToCartFragment())
    }

    companion object {
        const val TAG = "ProductFragment"
    }
}