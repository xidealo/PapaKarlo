package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.data.enums.ProductCode
import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.domain.repository.menu_product.MenuProductRepo
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.MenuFragmentDirections.toProductFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

abstract class ProductTabViewModel : BaseViewModel() {

    abstract val productListState: StateFlow<State<List<MenuProduct>>>

    abstract fun getMenuProductList(productCode: ProductCode)
    abstract fun onProductClicked(menuProduct: MenuProduct)
}

class ProductTabViewModelImpl @Inject constructor(private val menuProductRepo: MenuProductRepo) :
    ProductTabViewModel() {

    override val productListState: MutableStateFlow<State<List<MenuProduct>>> =
        MutableStateFlow(State.Loading())

    override fun getMenuProductList(productCode: ProductCode) {
        menuProductRepo.getMenuProductListAsFlow()
            .onEach { menuProductList ->
                if (menuProductList.isEmpty()) {
                    productListState.value = State.Loading()
                } else {
                    if (productCode == ProductCode.ALL) {
                        productListState.value = State.Success(menuProductList)
                    } else {
                        val filteredMenuProductList = menuProductList.filter { menuProduct ->
                            menuProduct.productCode == productCode.name
                        }
                        if (filteredMenuProductList.isEmpty()) {
                            productListState.value = State.Empty()
                        } else {
                            productListState.value = filteredMenuProductList.toStateSuccess()
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    override fun onProductClicked(menuProduct: MenuProduct) {
        router.navigate(toProductFragment(menuProduct, menuProduct.name))
    }
}