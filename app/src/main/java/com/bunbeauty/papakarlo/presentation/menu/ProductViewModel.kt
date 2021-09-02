package com.bunbeauty.papakarlo.presentation.menu

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.model.ui.product.MenuProduct
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.repo.MenuProductRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.ui.model.MenuProductUI
import com.bunbeauty.presentation.util.string.IStringUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val menuProductRepo: MenuProductRepo,
    cartProductRepo: CartProductRepo,
    stringUtil: IStringUtil,
    productHelper: IProductHelper,
) : CartViewModel(cartProductRepo, stringUtil, productHelper) {

    private val mutableMenuProduct: MutableStateFlow<MenuProductUI?> = MutableStateFlow(null)
    val menuProduct: StateFlow<MenuProductUI?> = mutableMenuProduct.asStateFlow()

    fun getMenuProduct(menuProductUuid: String) {
        viewModelScope.launch {
            mutableMenuProduct.value = menuProductRepo.getMenuProduct(menuProductUuid)?.toUI()
        }
    }

    private fun MenuProduct.toUI(): MenuProductUI {
        val oldPrice = productHelper.getMenuProductOldPrice(this)?.let { oldPrice ->
            stringUtil.getCostString(oldPrice)
        }
        val newPrice = productHelper.getMenuProductNewPrice(this)

        return MenuProductUI(
            name = name,
            size = stringUtil.getSizeString(weight),
            oldPrice = oldPrice,
            newPrice = stringUtil.getCostString(newPrice),
            description = description,
        )
    }
}