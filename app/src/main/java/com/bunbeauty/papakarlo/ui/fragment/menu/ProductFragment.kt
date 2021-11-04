package com.bunbeauty.papakarlo.ui.fragment.menu

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.databinding.FragmentProductBinding
import com.bunbeauty.papakarlo.delegates.argument
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.strikeOutText
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.menu.ProductViewModel
import com.bunbeauty.papakarlo.ui.base.TopbarCartFragment
import com.bunbeauty.presentation.util.string.IStringUtil
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProductFragment : TopbarCartFragment<FragmentProductBinding>() {

    @Inject
    lateinit var stringUtil: IStringUtil

    override val isCartVisible = true
    override val isBottomBarVisible = true

    override val viewModel: ProductViewModel by viewModels { viewModelFactory }

    private val menuProductUuid: String by argument()
    private val photo: Bitmap by argument()

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.menuProduct.onEach { menuProduct ->
            viewDataBinding.run {
                val isLoading = menuProduct == null
                fragmentProductPbLoading.toggleVisibility(isLoading)
                fragmentProductCvMain.toggleVisibility(!isLoading)
                fragmentProductBtnAdd.toggleVisibility(!isLoading)

                if (menuProduct != null) {
                    fragmentProductIvPhoto.setImageBitmap(photo)
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
        viewDataBinding.fragmentProductBtnAdd.setOnClickListener {
            viewModel.addProductToCart(menuProductUuid)
        }
    }
}