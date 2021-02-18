package com.bunbeauty.papakarlo.presentation.menu

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.model.product.MenuProduct
import com.bunbeauty.domain.repo.Api
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
    @Api private val menuProductRepo: MenuProductRepo,
    private val  stringUtil: IStringUtil,
    private val  productHelper: IProductHelper,
) : CartViewModel() {

    private val mutableMenuProduct: MutableStateFlow<MenuProductUI?> = MutableStateFlow(null)
    val menuProduct: StateFlow<MenuProductUI?> = mutableMenuProduct.asStateFlow()

    fun getMenuProduct(menuProductUuid: String) {
        viewModelScope.launch {
            mutableMenuProduct.value = menuProductRepo.getMenuProductByUuid(menuProductUuid)?.toUI()
        }
    }

    private fun MenuProduct.toUI(): MenuProductUI {
        return MenuProductUI(
            name = name,
            size = stringUtil.getSizeString(nutrition),
            oldPrice = stringUtil.getCostString(oldPrice),
            newPrice = stringUtil.getCostString(newPrice),
            description = description,
        )
    }
}