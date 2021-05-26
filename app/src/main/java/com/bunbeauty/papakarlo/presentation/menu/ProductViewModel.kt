package com.bunbeauty.papakarlo.presentation.menu

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateNullableSuccess
import com.bunbeauty.domain.model.local.MenuProduct
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringHelper
import com.bunbeauty.papakarlo.presentation.base.TopbarCartViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    val stringHelper: IStringHelper,
    cartProductRepo: CartProductRepo,
    stringUtil: IStringHelper,
    productHelper: IProductHelper,
) : TopbarCartViewModel(cartProductRepo, stringUtil, productHelper) {

    private val _menuProductState: MutableStateFlow<State<MenuProduct?>> =
        MutableStateFlow(State.Loading())
    val menuProductState: StateFlow<State<MenuProduct?>>
        get() = _menuProductState.asStateFlow()

    fun getMenuProduct(menuProductUuid: String) {
        menuProductRepo.getMenuProductAsFlow(menuProductUuid).onEach { menuProduct ->
            if (menuProduct != null)
                _menuProductState.value = menuProduct.toStateNullableSuccess()
            else
                _menuProductState.value = State.Empty()
        }.launchIn(viewModelScope)
    }
}