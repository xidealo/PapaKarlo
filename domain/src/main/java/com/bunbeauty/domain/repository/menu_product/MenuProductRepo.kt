package com.bunbeauty.domain.repository.menu_product

import com.bunbeauty.data.enums.ProductCode
import com.bunbeauty.data.model.MenuProduct
import kotlinx.coroutines.flow.Flow

interface MenuProductRepo {

    suspend fun insert(menuProduct: MenuProduct)

    suspend fun getMenuProductRequest()
    fun getMenuProductListAsFlow(productCode: ProductCode): Flow<List<MenuProduct>>
}