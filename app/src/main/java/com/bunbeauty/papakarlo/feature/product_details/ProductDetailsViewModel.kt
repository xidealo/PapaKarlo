package com.bunbeauty.papakarlo.feature.product_details

import androidx.lifecycle.SavedStateHandle
import com.bunbeauty.domain.interactor.menu_product.IMenuProductInteractor
import com.bunbeauty.shared.domain.model.product.MenuProduct
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

    private val mutableMenuProduct: MutableStateFlow<MenuProductUI?> = MutableStateFlow(null)
    val menuProduct: StateFlow<MenuProductUI?> = mutableMenuProduct.asStateFlow()

    init {
        savedStateHandle.get<String>("menuProductUuid")?.let { menuProductUuid ->
            observeMenuProduct(menuProductUuid)
        }
    }

    fun onWantClicked(menuProductUI: MenuProductUI) {
        addProductToCart(menuProductUI.uuid)
    }

    private fun observeMenuProduct(menuProductUuid: String) {
        menuProductInteractor.observeMenuProductByUuid(menuProductUuid)
            .launchOnEach { menuProduct ->
                mutableMenuProduct.value = menuProduct?.toUI()
            }
    }

    private fun MenuProduct.toUI(): MenuProductUI {
        return MenuProductUI(
            uuid = uuid,
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