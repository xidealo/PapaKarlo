package com.bunbeauty.papakarlo.feature.product_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.databinding.FragmentProductDetailsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.setPhoto
import com.bunbeauty.papakarlo.extensions.strikeOutText
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.util.string.IStringUtil
import javax.inject.Inject

class ProductDetailsFragment : BaseFragment(R.layout.fragment_product_details) {

    @Inject
    lateinit var stringUtil: IStringUtil

    override val viewModel: ProductDetailsViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(FragmentProductDetailsBinding::bind)

    private val menuProductUuid: String by argument()
    private val photoLink: String by argument()

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.menuProduct.startedLaunch { menuProduct ->
            viewBinding.run {
                val isLoading = menuProduct == null
                fragmentProductDetailsPbLoading.toggleVisibility(isLoading)
                fragmentProductDetailsCvMain.toggleVisibility(!isLoading)
                fragmentProductDetailsBtnAdd.toggleVisibility(!isLoading)

                if (menuProduct != null) {
                    fragmentProductDetailsIvPhoto.setPhoto(photoLink)
                    fragmentProductDetailsTvName.text = menuProduct.name
                    fragmentProductDetailsTvSize.text = menuProduct.size
                    if (menuProduct.oldPrice == null) {
                        fragmentProductDetailsTvOldPrice.gone()
                    } else {
                        fragmentProductDetailsTvOldPrice.text = menuProduct.oldPrice
                        fragmentProductDetailsTvOldPrice.strikeOutText()
                    }
                    fragmentProductDetailsTvNewPrice.text = menuProduct.newPrice
                    fragmentProductDetailsTvDescription.text = menuProduct.description
                }
            }
        }
        viewModel.getMenuProduct(menuProductUuid)
        viewBinding.fragmentProductDetailsBtnAdd.setOnClickListener {
            viewModel.addProductToCart(menuProductUuid)
        }
    }
}