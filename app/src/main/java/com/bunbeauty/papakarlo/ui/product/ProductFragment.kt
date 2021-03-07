package com.bunbeauty.papakarlo.ui.product

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.databinding.FragmentProductBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.invisible
import com.bunbeauty.papakarlo.ui.base.CartClickableFragment
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.ui.product.ProductFragmentDirections.toCartFragment
import com.bunbeauty.papakarlo.utils.product.IProductHelper
import com.bunbeauty.papakarlo.utils.string.IStringHelper
import com.bunbeauty.papakarlo.view_model.ProductViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ProductFragment : CartClickableFragment<FragmentProductBinding, ProductViewModel>(),
    ProductNavigator {

    override lateinit var title: String

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var stringHelper: IStringHelper

    @Inject
    lateinit var productHelper: IProductHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val product = ProductFragmentArgs.fromBundle(requireArguments()).menuProduct
        title = product.name

        super.onViewCreated(view, savedInstanceState)

        if (product.weight == 0)
            viewDataBinding.fragmentProductTvWeight.invisible()

        viewDataBinding.product = product
        viewDataBinding.stringHelper = stringHelper
        viewDataBinding.productHelper = productHelper
        viewDataBinding.fragmentProductTvOldCost.paintFlags =
            viewDataBinding.fragmentProductTvOldCost.paintFlags or STRIKE_THRU_TEXT_FLAG
        viewModel.navigator = WeakReference(this)
        viewDataBinding.fragmentProductBtnAdd.setOnClickListener {
            viewModel.addProductToCart(product)
            (activity as MainActivity).showMessage("${product.name} был добавлен в корзину")
        }
    }

    override fun goToCart(view: View) {
        findNavController().navigate(toCartFragment())
    }
}