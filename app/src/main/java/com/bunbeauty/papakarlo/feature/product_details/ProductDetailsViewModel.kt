package com.bunbeauty.papakarlo.feature.product_details

import androidx.lifecycle.SavedStateHandle
import com.bunbeauty.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.domain.model.product.MenuProduct
import com.bunbeauty.papakarlo.common.view_model.CartViewModel
import com.bunbeauty.papakarlo.util.string.IStringUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductDetailsViewModel(
    private val menuProductInteractor: IMenuProductInteractor,
    private val stringUtil: IStringUtil,
    savedStateHandle: SavedStateHandle
) : CartViewModel() {

    private val menuProductUuid: String = savedStateHandle["menuProductUuid"] ?: ""

    private val mutableMenuProduct: MutableStateFlow<MenuProductUI?> = MutableStateFlow(null)
    val menuProduct: StateFlow<MenuProductUI?> = mutableMenuProduct.asStateFlow()

    init {
        observeMenuProduct(menuProductUuid)
    }

    fun onWantClicked() {
        addProductToCart(menuProductUuid)
    }

    private fun observeMenuProduct(menuProductUuid: String) {
        menuProductInteractor.observeMenuProductByUuid(menuProductUuid)
            .launchOnEach { menuProduct ->
                mutableMenuProduct.value = menuProduct?.toUI()
            }
    }

    private fun MenuProduct.toUI(): MenuProductUI {
        return MenuProductUI(
            photoLink = photoLink,
            name = name,
            size = "$nutrition $utils",
            oldPrice = oldPrice?.let { price ->
                stringUtil.getCostString(price)
            },
            newPrice = stringUtil.getCostString(newPrice),
            description = description,
        )
    }
}