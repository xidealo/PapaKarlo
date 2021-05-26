package com.bunbeauty.papakarlo.presentation.menu

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.data.mapper.adapter.MenuProductAdapterMapper
import com.bunbeauty.domain.enums.ProductCode
import com.bunbeauty.domain.model.adapter.MenuProductAdapterModel
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringHelper
import com.bunbeauty.papakarlo.presentation.base.TopbarCartViewModel
import com.bunbeauty.papakarlo.ui.MenuFragmentDirections.toProductFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

abstract class ProductTabViewModel(
    cartProductRepo: CartProductRepo,
    stringUtil: IStringHelper,
    productHelper: IProductHelper,
) : TopbarCartViewModel(cartProductRepo, stringUtil, productHelper) {

    abstract val productListState: StateFlow<State<List<MenuProductAdapterModel>>>

    abstract fun getMenuProductList(productCode: ProductCode)
    abstract fun onProductClicked(menuProductAdapterModel: MenuProductAdapterModel)
}

class ProductTabViewModelImpl @Inject constructor(
    private val mapper: MenuProductAdapterMapper,
    cartProductRepo: CartProductRepo,
    stringUtil: IStringHelper,
    productHelper: IProductHelper,
) : ProductTabViewModel(cartProductRepo, stringUtil, productHelper) {

    override val productListState: MutableStateFlow<State<List<MenuProductAdapterModel>>> =
        MutableStateFlow(State.Loading())

    override fun getMenuProductList(productCode: ProductCode) {
        menuProductRepo.getMenuProductListAsFlow().onEach { menuProductList ->
            if (menuProductList.isEmpty()) {
                productListState.value = State.Loading()
            } else {
                if (productCode == ProductCode.ALL) {
                    productListState.value =
                        menuProductList.map { mapper.from(it) }.toStateSuccess()
                } else {
                    val filteredMenuProductList = menuProductList.filter { menuProduct ->
                        menuProduct.productCode == productCode.name
                    }
                    if (filteredMenuProductList.isEmpty()) {
                        productListState.value = State.Empty()
                    } else {
                        productListState.value =
                            filteredMenuProductList.map { mapper.from(it) }.toStateSuccess()
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onProductClicked(menuProductAdapterModel: MenuProductAdapterModel) {
        router.navigate(
            toProductFragment(
                menuProductAdapterModel.uuid,
                menuProductAdapterModel.name
            )
        )
    }
}