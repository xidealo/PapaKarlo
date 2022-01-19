package com.bunbeauty.papakarlo.ui.fragment.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentProductBinding
import com.bunbeauty.papakarlo.delegates.argument
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.setPhoto
import com.bunbeauty.papakarlo.extensions.strikeOutText
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.menu.ProductViewModel
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.presentation.util.string.IStringUtil
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProductFragment : BaseFragment(R.layout.fragment_product) {

    @Inject
    lateinit var stringUtil: IStringUtil

    override val viewModel: ProductViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(FragmentProductBinding::bind)

    private val menuProductUuid: String by argument()
    private val photoLink: String by argument()

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.menuProduct.onEach { menuProduct ->
            viewBinding.run {
                val isLoading = menuProduct == null
                fragmentProductPbLoading.toggleVisibility(isLoading)
                fragmentProductCvMain.toggleVisibility(!isLoading)
                fragmentProductBtnAdd.toggleVisibility(!isLoading)

                if (menuProduct != null) {
                    fragmentProductIvPhoto.setPhoto(photoLink)
                    fragmentProductTvName.text = menuProduct.name
                    fragmentProductTvSize.text = menuProduct.size
                    if (menuProduct.oldPrice == null) {
                        fragmentProductTvOldPrice.gone()
                    } else {
                        fragmentProductTvOldPrice.text = menuProduct.oldPrice
                        fragmentProductTvOldPrice.strikeOutText()
                    }
                    fragmentProductTvNewPrice.text = menuProduct.newPrice
                    fragmentProductTvDescription.text = menuProduct.description
                }
            }
        }.startedLaunch()
        viewModel.getMenuProduct(menuProductUuid)
        viewBinding.fragmentProductBtnAdd.setOnClickListener {
            viewModel.addProductToCart(menuProductUuid)
        }
    }
}