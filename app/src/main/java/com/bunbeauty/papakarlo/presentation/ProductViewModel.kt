package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateNullableSuccess
import com.bunbeauty.domain.model.local.MenuProduct
import com.bunbeauty.domain.repo.MenuProductRepo
import com.bunbeauty.domain.util.string_helper.IStringHelper
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val menuProductRepo: MenuProductRepo,
    val stringHelper: IStringHelper
) : ToolbarViewModel() {

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