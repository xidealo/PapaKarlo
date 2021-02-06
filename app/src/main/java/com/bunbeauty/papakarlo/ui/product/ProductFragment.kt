package com.bunbeauty.papakarlo.ui.product

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.databinding.FragmentProductBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.CartClickableFragment
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.ui.product.ProductFragmentDirections.toCartFragment
import com.bunbeauty.papakarlo.utils.string.IStringHelper
import com.bunbeauty.papakarlo.view_model.ProductViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ProductFragment : CartClickableFragment<FragmentProductBinding, ProductViewModel>(),
    ProductNavigator {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_product
    override var viewModelClass = ProductViewModel::class.java
    override lateinit var title: String

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var iStringHelper: IStringHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val product = ProductFragmentArgs.fromBundle(requireArguments()).menuProduct
        title = product.name

        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.product = product
        viewDataBinding.iStringHelper = iStringHelper
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